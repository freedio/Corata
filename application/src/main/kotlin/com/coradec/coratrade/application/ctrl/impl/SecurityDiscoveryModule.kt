/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.application.ctrl.impl

import com.coradec.coradeck.bus.meta.reachedState
import com.coradec.coradeck.bus.model.BusNodeState.ATTACHED
import com.coradec.coradeck.bus.model.impl.BasicBusModule
import com.coradec.coradeck.com.model.Recipient
import com.coradec.coradeck.conf.model.Property
import com.coradec.coradeck.core.ctrl.Origin
import com.coradec.coradeck.core.util.here
import com.coradec.coradeck.core.util.relax
import com.coradec.coradeck.ctrl.com.Request
import com.coradec.coradeck.ctrl.com.RequestState.CANCELLED
import com.coradec.coradeck.ctrl.com.RequestState.FAILED
import com.coradec.coradeck.ctrl.com.RequestState.SUCCESSFUL
import com.coradec.coradeck.ctrl.com.Voucher
import com.coradec.coradeck.ctrl.com.impl.BasicCommand
import com.coradec.coradeck.ctrl.module.CoraControl
import com.coradec.coradeck.ctrl.trouble.RequestCancelledException
import com.coradec.coradeck.ctrl.trouble.RequestFailedException
import com.coradec.coradeck.data.view.PersistenceView
import com.coradec.coradeck.text.model.LocalizedText
import com.coradec.coratrade.application.app.Application
import com.coradec.coratrade.application.model.ContractDescriptor
import com.coradec.coratrade.interactive.comm.ctrl.impl.SymbolQueryVoucher
import com.coradec.coratrade.interactive.ctrl.RequestDispatcher
import java.io.IOException
import java.nio.file.FileVisitResult
import java.nio.file.FileVisitResult.CONTINUE
import java.nio.file.FileVisitResult.TERMINATE
import java.nio.file.FileVisitor
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.attribute.BasicFileAttributes
import java.time.Duration

class SecurityDiscoveryModule : BasicBusModule() {
    val persistence: PersistenceView by reachedState(ATTACHED) { Application.persistence }
    var lastInjection = 0L
    val openVouchers = ArrayList<Request>()

    override fun onInitializing() {
        super.onInitializing()
        approve(SecurityDiscoveryCommand::class.java)
    }

    override fun onReady() {
        super.onReady()
        inject(SecurityDiscoveryCommand(here, this))
    }

    inner class SecurityDiscoveryCommand(origin: Origin, recipient: Recipient) : BasicCommand(origin, recipient) {
        var processedFiles = 0
        override fun execute() {
            val basedir = PROP_INPUT_DIRECTORY.value(VAL_USER_DIR, VAL_CURR_DIR)
            debug("Discovering securities in directory %s...", basedir)
            val filesToDelete: MutableList<Path> = ArrayList()
            try {
                Files.walkFileTree(basedir, object : FileVisitor<Path> {
                    override fun postVisitDirectory(path: Path, problem: IOException?): FileVisitResult {
                        if (path != basedir) {
                            filesToDelete.add(path)
                        }
                        return if (running) CONTINUE else TERMINATE
                    }

                    override fun visitFile(path: Path, fileAttrs: BasicFileAttributes): FileVisitResult {
                        if (processFile(path, fileAttrs)) filesToDelete.add(path).also { ++processedFiles }
                        return if (running) CONTINUE else TERMINATE
                    }

                    override fun visitFileFailed(path: Path, problem: IOException?): FileVisitResult {
                        error(TEXT_FAILED_TO_READ_FILE, path)
                        return if (running) CONTINUE else TERMINATE
                    }

                    override fun preVisitDirectory(path: Path, fileAttrs: BasicFileAttributes): FileVisitResult {
                        return if (running) CONTINUE else TERMINATE
                    }
                })
                filesToDelete.forEach { file ->
                    Files.delete(file)
                }
                info(TEXT_PROCESSED, processedFiles, filesToDelete.size)
                debug("Finished discovering securities in directory %s.", basedir)
                succeed()
            } catch (e: Exception) {
                fail(e)
            } finally {
                CoraControl.createRequestList(here, this@SecurityDiscoveryModule, openVouchers).standby()
                persistence.commit().standby()
                finish()
            }
        }

        private fun processFile(path: Path, fileAttrs: BasicFileAttributes): Boolean {
            val filename = path.toString()
            return when {
                filename.endsWith(".txt") -> {
                    return processTextFile(path)
                }
                else -> {
                    error(TEXT_FILE_NOT_PROCESSED, path)
                    false
                }
            }
        }

        private fun processTextFile(path: Path): Boolean {
            Files.readAllLines(path).forEach { line ->
                line.replaceFirst(Regex("[\t\r,;=:].*$"), "").let { pattern ->
                    val corrected = pattern.substringBefore(' ')
                    debug("Discovering securities from file «%s»...", corrected)
                    debug("Status: %s", if (running) "running" else if (stopping) "stopping" else this@SecurityDiscoveryModule.state.name)
                    waitUpto1SecondIfNecessary()
                    val voucher = SymbolQueryVoucher(here, corrected)
                    openVouchers += voucher
                    RequestDispatcher.inject(voucher).whenComplete { state, value, problem ->
                        when (state) {
                            SUCCESSFUL -> {
                                debug("Pattern «%s» → «%s» → value %s", pattern, corrected, value.let {
                                    if (it == null) "missing" else "«$it»"
                                })
                                value?.forEach { entry ->
                                    debug("Persisting contract $entry...")
                                    persistence += ContractDescriptor.of(entry)
                                    debug("Persisted contract $entry.")
                                }
                            }
                            FAILED -> throw problem ?: RequestFailedException()
                            CANCELLED -> throw RequestCancelledException()
                            else -> relax()
                        }
                    }
                }
            }
            if (stopping) {
                warn(TEXT_FILE_PROCESSING_INTERRUPTED, path)
            }
            return running
        }

        private fun waitUpto1SecondIfNecessary() {
            val nextInjection = lastInjection + 1000L
            val delay = nextInjection - System.currentTimeMillis()
            lastInjection = System.currentTimeMillis()
            if (delay > 0) Thread.sleep(delay)
        }
    }

    companion object {
        private val TEXT_FILE_NOT_PROCESSED = LocalizedText.define("FileNotProcessed")
        private val TEXT_FAILED_TO_READ_FILE = LocalizedText.define("FailedToReadFile")
        private val TEXT_FILE_PROCESSING_INTERRUPTED = LocalizedText.define("TextProcessingInterrupted")
        private val TEXT_PROCESSED = LocalizedText.define("Processed")
        private val PROP_INPUT_DIRECTORY = Property.define("InputDirectory", Path::class.java)
        private val VAL_USER_DIR = System.getProperty("user.home")
        private val VAL_CURR_DIR = System.getProperty("user.dir")
    }
}
