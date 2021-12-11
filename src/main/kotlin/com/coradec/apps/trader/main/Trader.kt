package com.coradec.apps.trader.main

import com.coradec.apps.trader.ctrl.AccountManager
import com.coradec.apps.trader.ctrl.ContractLoader
import com.coradec.apps.trader.ctrl.ContractProcessor
import com.coradec.apps.trader.ctrl.ContractUpdater
import com.coradec.apps.trader.ibkr.ctrl.InteractiveBroker
import com.coradec.apps.trader.model.AccountDefinition
import com.coradec.apps.trader.model.DatabaseParameters
import com.coradec.apps.trader.model.DbQuote
import com.coradec.apps.trader.model.Title
import com.coradec.apps.trader.model.impl.BasicAccountDefinition
import com.coradec.apps.trader.model.impl.BasicDbQuote
import com.coradec.apps.trader.model.impl.BasicTitle
import com.coradec.coradeck.bus.model.BusNode
import com.coradec.coradeck.bus.model.impl.BasicBusApplication
import com.coradec.coradeck.bus.module.CoraBus
import com.coradec.coradeck.bus.module.CoraBusImpl
import com.coradec.coradeck.com.model.impl.Syslog
import com.coradec.coradeck.com.module.CoraComImpl
import com.coradec.coradeck.conf.model.LocalProperty
import com.coradec.coradeck.conf.module.CoraConfImpl
import com.coradec.coradeck.ctrl.module.CoraControl
import com.coradec.coradeck.ctrl.module.CoraControlImpl
import com.coradec.coradeck.db.model.Database
import com.coradec.coradeck.db.model.RecordTable
import com.coradec.coradeck.db.module.CoraDB
import com.coradec.coradeck.db.module.CoraDbHsql
import com.coradec.coradeck.dir.module.CoraDirImpl
import com.coradec.coradeck.module.model.CoraModules
import com.coradec.coradeck.text.module.CoraTextImpl
import com.coradec.coradeck.type.model.Password
import com.coradec.coradeck.type.module.impl.CoraTypeImpl
import java.net.URI
import java.nio.file.Paths
import javax.swing.JOptionPane.*
import kotlin.io.path.exists
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
import kotlin.system.exitProcess

fun main(vararg args: String) {
    CoraModules.register(CoraConfImpl(), CoraComImpl(), CoraTextImpl(), CoraTypeImpl(), CoraControlImpl(), CoraDirImpl(), CoraBusImpl(), CoraDbHsql())
    try {
        Trader(args.toList())
    } catch (e: Exception) {
        Syslog.error(e)
        exitProcess(1)
    }
}

class Trader(args: List<String>) : BasicBusApplication("Trader", args) {
    private val db: Database = CoraDB.database(dbURI, dbUserName, dbPassword)

    override fun onInitializing() {
        super.onInitializing()
        add("Database", db.memberView)
    }

    override fun onDetached() {
        super.onDetached()
        exitProcess(0)
    }

    override fun onLoaded() {
        super.onLoaded()
        if (Paths.get("/tmp/initial.txt").exists()) {
            debug("*** DB RESET ***")
            db.reset()
        }
        val contracts: RecordTable<Title> = db.openTable(BasicTitle::class)
        val quotes: RecordTable<DbQuote> = db.openTable(BasicDbQuote::class)
        val accounts: RecordTable<AccountDefinition> = db.openTable(BasicAccountDefinition::class)
        CoraBus.machineBus.add("InteractiveBroker", InteractiveBroker.memberView)
        CoraBus.machineBus.add("RequestHandler", PROP_REQUEST_HANDLER.value.createInstance().memberView)
        add("ContractLoader", ContractLoader(contracts).memberView)
        add("ContractProcessor", ContractProcessor(contracts, quotes).memberView)
        add("ContractUpdater", ContractUpdater(contracts, quotes).memberView)
        add("AccountManager", AccountManager(accounts).memberView)
    }

    override fun run() {
        debug("Starting Trader.")
        do {
            Thread.sleep(5000)
            val reaction = showConfirmDialog(null, "Click Cancel to finish Trader, OK to show CIMMEX stats.", "Stats", OK_CANCEL_OPTION)
            IMMEX.printFullStats()
        } while (reaction == OK_OPTION)
        detach()
    }

    companion object {
        private val IMMEX = CoraControl.IMMEX
        private val PROP_DB_URI = LocalProperty<DatabaseParameters>("DB")
        private val PROP_REQUEST_HANDLER = LocalProperty<KClass<BusNode>>("RequestHandler")
        private val dbURI: URI get() = PROP_DB_URI.value.URI
        private val dbUserName: String get() = PROP_DB_URI.value.Username
        private val dbPassword: Password get() = PROP_DB_URI.value.Password
    }
}
