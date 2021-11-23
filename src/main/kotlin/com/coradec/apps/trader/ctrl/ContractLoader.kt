package com.coradec.apps.trader.ctrl

import com.coradec.apps.trader.com.impl.HandleTitleRequest
import com.coradec.apps.trader.com.impl.UpdateTitleRequest
import com.coradec.apps.trader.ibkr.ctrl.SwitchBoard
import com.coradec.apps.trader.model.SecurityType
import com.coradec.apps.trader.model.Title
import com.coradec.apps.trader.model.TitleState
import com.coradec.apps.trader.model.impl.BasicTitle
import com.coradec.coradeck.bus.model.impl.BasicBusEngine
import com.coradec.coradeck.core.util.here
import com.coradec.coradeck.core.util.toPath
import com.coradec.coradeck.ctrl.module.CoraControl
import com.coradec.coradeck.db.model.Database
import com.coradec.coradeck.db.model.RecordTable
import java.nio.file.Files
import java.nio.file.Path
import java.time.ZonedDateTime
import java.util.*
import kotlin.io.path.exists

class ContractLoader(val db: Database): BasicBusEngine() {
    private lateinit var titles: RecordTable<Title>

    override fun onInitializing() {
        super.onInitializing()
        titles = db.openTable(BasicTitle::class)
    }

    override fun onFinalized() {
        titles.close()
        super.onFinalized()
    }

    override fun run() {
        debug("TitleLoader running.")
        loadTitleFile("/tmp/initial.txt".toPath())
        loadTitleFile("/tmp/titles.txt".toPath())
        titles
            .sortedByDescending { title -> title.state }
            .forEach { title ->
                debug("TitleLoader: will update ‹%s›.", title)
                if (title.state != TitleState.DISABLED) {
                    IMMEX.inject(UpdateTitleRequest(here, title))
                    if (title.state == TitleState.ACTIVE) IMMEX.inject(HandleTitleRequest(here, title))
                }
            }
    }

    private fun loadTitleFile(path: Path) {
        if (path.exists()) {
            debug("Loading title file «%s»...", path)
            Files.newInputStream(path).bufferedReader().lines().forEach { title ->
                debug("Loading title ‹%s›", title)
                val (symbol, exchgStr, typeStr, ccy) = title.split(':')
                val exchg = exchgStr.ifBlank { null }
                val type = SecurityType(typeStr)
                val currency = Currency.getInstance(ccy)
                val now = ZonedDateTime.now()
                val state = TitleState.INACTIVE
                titles.insert(BasicTitle(symbol, type, currency, state, SwitchBoard.defaultFrequency, now, null, exchg))
            }
            titles.commit()
            Files.delete(path)
        } else debug("File «%s» not found: skipping.", path)
    }

    companion object {
        private val IMMEX = CoraControl.IMMEX
    }
}
