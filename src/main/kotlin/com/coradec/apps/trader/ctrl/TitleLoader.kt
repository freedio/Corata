package com.coradec.apps.trader.ctrl

import com.coradec.apps.trader.com.impl.HandleTitleRequest
import com.coradec.apps.trader.com.impl.UpdateTitleRequest
import com.coradec.apps.trader.ibkr.ctrl.SwitchBoard
import com.coradec.apps.trader.main.StartCommand
import com.coradec.apps.trader.model.SecurityType
import com.coradec.apps.trader.model.Title
import com.coradec.apps.trader.model.TitleState.*
import com.coradec.apps.trader.model.impl.BasicTitle
import com.coradec.coradeck.bus.model.impl.BasicBusEngine
import com.coradec.coradeck.core.model.ClassPathResource
import com.coradec.coradeck.core.util.here
import com.coradec.coradeck.ctrl.module.CoraControl
import com.coradec.coradeck.ctrl.module.unsubscribe
import com.coradec.coradeck.db.model.Database
import com.coradec.coradeck.db.model.RecordTable
import java.time.ZonedDateTime
import java.util.*

class TitleLoader(val db: Database) : BasicBusEngine() {
    private lateinit var titles: RecordTable<Title>

    override fun onInitializing() {
        super.onInitializing()
        titles = db.openTable(BasicTitle::class)
    }

    override fun onFinalized() {
        unsubscribe()
        unroute(StartCommand::class)
        super.onFinalized()
    }

    override fun run() {
        debug("TitleLoader running.")
        ClassPathResource(this::class, "/initial.txt").ifExists { loadTitleFile() }
        ClassPathResource(this::class, "/titles.txt").ifExists { loadTitleFile() }
        titles
            .sortedByDescending { title -> title.state }
            .forEach { title ->
                debug("TitleLoader: triggering title ‹%s›.", title)
                if (title.state != DISABLED) {
                    IMMEX.inject(UpdateTitleRequest(here, title))
                    if (title.state == ACTIVE) IMMEX.inject(HandleTitleRequest(here, title))
                }
            }
    }

    private fun ClassPathResource.loadTitleFile() {
        lines.forEach { title ->
            debug("Loading title ‹%s›", title)
            val (symbol, exchgStr, typeStr, ccy) = title.split(':')
            val exchg = exchgStr.ifBlank { null }
            val type = SecurityType(typeStr)
            val currency = Currency.getInstance(ccy)
            val now = ZonedDateTime.now()
            val state = INACTIVE // if (exchg == "EBS") INACTIVE else DISABLED
            titles.insert(BasicTitle(symbol, type, currency, state, SwitchBoard.defaultFrequency, now, null, exchg))
        }
        titles.commit()
        delete()
    }

    companion object {
        private val IMMEX = CoraControl.IMMEX
    }
}
