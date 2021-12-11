package com.coradec.apps.trader.ctrl

import com.coradec.apps.trader.com.impl.HandleTitleRequest
import com.coradec.apps.trader.com.impl.UpdateContractRequest
import com.coradec.apps.trader.ibkr.ctrl.SwitchBoard
import com.coradec.apps.trader.model.SecurityType
import com.coradec.apps.trader.model.Title
import com.coradec.apps.trader.model.TitleState
import com.coradec.apps.trader.model.impl.BasicTitle
import com.coradec.coradeck.bus.model.impl.BasicBusEngine
import com.coradec.coradeck.core.util.here
import com.coradec.coradeck.core.util.toPath
import com.coradec.coradeck.ctrl.module.CoraControl
import com.coradec.coradeck.db.model.RecordTable
import java.nio.file.Files
import java.nio.file.Path
import java.time.ZonedDateTime
import java.util.*
import kotlin.io.path.exists

class ContractLoader(private val contracts: RecordTable<Title>) : BasicBusEngine() {

    override fun run() {
        debug("TitleLoader running.")
        (loadTitleFile("/tmp/initial.txt".toPath()) || loadTitleFile("/tmp/titles.txt".toPath())) && wait()
        contracts
            .sortedByDescending { title -> title.state }
            .forEach { title ->
                debug("TitleLoader: will update ‹%s›.", title)
                if (title.state != TitleState.DISABLED) {
                    IMMEX.inject(UpdateContractRequest(here, title))
                    if (title.state == TitleState.ACTIVE) IMMEX.inject(HandleTitleRequest(here, title))
                }
            }
    }

    private fun loadTitleFile(path: Path): Boolean = path.exists().apply {
        if (this) {
            debug("Loading title file «%s»...", path)
            Files.newInputStream(path).bufferedReader().lines().forEach { title ->
                debug("Loading title ‹%s›", title)
                val (symbol, exchgStr, typeStr, ccy, primXchgStr) = title.split(':')
                val exchg = exchgStr.ifBlank { null }
                val primXchg = primXchgStr.ifBlank { null }
                val type = SecurityType(typeStr)
                val currency = Currency.getInstance(ccy)
                val now = ZonedDateTime.now()
                val state = TitleState.INACTIVE
                contracts.insert(
                    BasicTitle(symbol, type, currency, state, SwitchBoard.defaultFrequency, now, null, exchg, primXchg)
                )
            }
            contracts.standby()
            contracts.commit()
            Files.delete(path)
        } else debug("File «%s» not found: skipping.", path)
    }

    private fun wait() = true.also {
        contracts.standby()
    }

    companion object {
        private val IMMEX = CoraControl.IMMEX
    }
}
