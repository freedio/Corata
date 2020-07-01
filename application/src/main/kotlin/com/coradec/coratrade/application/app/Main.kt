/*
 * Copyright © 2020 by Coradec LLC.  All rights reserved.
 */

package com.coradec.coratrade.application.app

import com.coradec.coradeck.bus.meta.reachedState
import com.coradec.coradeck.bus.model.BusNodeState.DETACHED
import com.coradec.coradeck.bus.model.BusNodeState.INITIALIZED
import com.coradec.coradeck.bus.model.BusNodeState.READY
import com.coradec.coradeck.bus.model.ServiceScope.MACHINE
import com.coradec.coradeck.bus.model.impl.BasicBusApplication
import com.coradec.coradeck.com.ctrl.Observer
import com.coradec.coradeck.com.model.Information
import com.coradec.coradeck.conf.model.Property
import com.coradec.coradeck.core.util.relax
import com.coradec.coradeck.ctrl.com.RequestState.CANCELLED
import com.coradec.coradeck.ctrl.com.RequestState.FAILED
import com.coradec.coradeck.ctrl.com.RequestState.SUCCESSFUL
import com.coradec.coradeck.ctrl.module.CoraControl
import com.coradec.coradeck.data.module.CoraData
import com.coradec.coradeck.data.view.PersistenceView
import com.coradec.coradeck.hsqldb.ctrl.HsqlDbDriver
import com.coradec.coradeck.session.module.CoraSession
import com.coradec.coradeck.text.model.LocalizedText
import com.coradec.coratrade.application.ctrl.impl.DataEnrichmentModule
import com.coradec.coratrade.application.ctrl.impl.MarketDataModule
import com.coradec.coratrade.application.ctrl.impl.SecurityDiscoveryModule
import com.coradec.coratrade.application.ctrl.impl.SecurityRatingModule
import com.coradec.coratrade.application.ctrl.impl.TradeExecutionModule
import com.coradec.coratrade.application.ctrl.impl.TradingTimer
import com.coradec.coratrade.interactive.comm.model.trouble.ErrorEvent
import java.nio.file.Path
import java.time.Duration
import java.util.concurrent.CountDownLatch

fun main() {
    Application.start()
    Application.awaitState(READY)
    Application.detach()
    Application.awaitState(DETACHED)
}

object Application : BasicBusApplication("CoraTrade"), Observer {
    private val TEXT_IBKR_ERROR = LocalizedText.define("InteractiveApiError")
    private val PROP_DB_LOCATION = Property.define("DatabaseLocation", String::class.java)
    private val PROP_DB_TYPE = Property.define("DatabaseType", String::class.java)
    private val PROP_DB_USERNAME = Property.define("DatabaseUsername", String::class.java)
    private val PROP_DB_PASSWORD = Property.define("DatabasePassword", String::class.java)
    private val dbPath = Path.of(PROP_DB_LOCATION.value())
    private val dbType = PROP_DB_TYPE.value()
    private val username = PROP_DB_USERNAME.value()
    private val password = PROP_DB_PASSWORD.value()
    private val latch = CountDownLatch(1)
    val persistence: PersistenceView by reachedState(INITIALIZED) { CoraData.getPersistence(CoraSession.new) }

    override fun onInitializing() {
        super.onInitializing()
        CoraControl.messageQueue.subscribe(this, ErrorEvent::class.java)
    }

    override fun onLoading() {
        super.onLoading()
        add("Timer", TradingTimer())
        add("SecurityDiscovery", SecurityDiscoveryModule())
        add("MarketData", MarketDataModule())
        add("DataEnrichment", DataEnrichmentModule())
        add("SecurityRating", SecurityRatingModule())
        add("TradeExecution", TradeExecutionModule())
    }

    override fun onTerminating() {
        super.onTerminating()
        persistence.commit()
    }

    override fun onReady() {
        super.onReady()
        detail("Application is ready.")
//        Thread.sleep(Duration.ofSeconds(60).toMillis())
        detail("Shutting down application...")
        finish()
    }

    override fun onInitialized() {
        super.onInitialized()
        debug("Attaching persistence storage...")
        val driver = HsqlDbDriver(dbType, dbPath.resolve("db"), username, password, scope = MACHINE)
        CoraData.addPersistenceStorage("storage", driver).whenComplete { state, problem ->
            debug("Finished attaching persistence storage:")
            when (state) {
                SUCCESSFUL -> debug("Persistence storage attached.")
                FAILED -> debug("Failed to attach persistence storage!")
                CANCELLED -> debug("Persistence storage attachment was cancelled!")
                else -> relax()
            }
        }
    }

    override fun notify(info: Information): Boolean {
        detail("Received event «%s»", info)
        if (info is ErrorEvent) {
            error(TEXT_IBKR_ERROR, info.errorCode, info.errorMessage)
        }
        return false
    }

}
