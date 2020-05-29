/*
 * Copyright © 2020 by Coradec LLC.  All rights reserved.
 */

package com.coradec.coratrade.application.app

import com.coradec.coradeck.bus.meta.reachedState
import com.coradec.coradeck.bus.model.BusNodeState.ATTACHED
import com.coradec.coradeck.bus.model.BusNodeState.INITIALIZED
import com.coradec.coradeck.bus.model.BusNodeState.INITIALIZING
import com.coradec.coradeck.bus.model.ServiceScope.MACHINE
import com.coradec.coradeck.bus.model.impl.BasicBusApplication
import com.coradec.coradeck.com.model.Recipient
import com.coradec.coradeck.conf.model.Property
import com.coradec.coradeck.core.ctrl.Origin
import com.coradec.coradeck.core.util.here
import com.coradec.coradeck.ctrl.com.impl.BasicCommand
import com.coradec.coradeck.data.module.CoraData
import com.coradec.coradeck.hsqldb.ctrl.HsqlDbDriver
import com.coradec.coradeck.session.module.CoraSession
import com.coradec.coratrade.application.ctrl.impl.DataEnrichmentModule
import com.coradec.coratrade.application.ctrl.impl.MarketDataModule
import com.coradec.coratrade.application.ctrl.impl.SecurityRatingModule
import com.coradec.coratrade.application.ctrl.impl.TradeExecutionModule
import com.coradec.coratrade.application.ctrl.impl.TradingTimer
import java.nio.file.Path

fun main() {
    Application.start()
}

object Application: BasicBusApplication("CoraTrade") {
    private val PROP_DB_LOCATION = Property.define("DatabaseLocation", String::class.java)
    private val PROP_DB_TYPE = Property.define("DatabaseType", String::class.java)
    private val PROP_DB_USERNAME = Property.define("DatabaseUsername", String::class.java)
    private val PROP_DB_PASSWORD = Property.define("DatabasePassword", String::class.java)
    private val dbPath = Path.of(PROP_DB_LOCATION.value())
    private val dbType = PROP_DB_TYPE.value()
    private val username = PROP_DB_USERNAME.value()
    private val password = PROP_DB_PASSWORD.value()
    val persistence by reachedState(INITIALIZING) {
        CoraData.getPersistence(CoraSession.new)
    }

    init {
        add("Timer", TradingTimer())
        add("MarketData", MarketDataModule())
        add("DataEnrichment", DataEnrichmentModule())
        add("SecurityRating", SecurityRatingModule())
        add("TradeExecution", TradeExecutionModule())
        debug("Attaching persistence storage")
        val driver = HsqlDbDriver(dbType, dbPath.resolve("db"), username, password, scope = MACHINE)
        CoraData.addPersistenceStorage("storage", driver)
    }

    override fun onStarted() {
        super.onStarted()
        detail("Application started.  Persistence = %s", persistence)
    }

}
