package com.coradec.apps.trader.main

import com.coradec.apps.trader.ctrl.AccountManager
import com.coradec.apps.trader.ctrl.ContractLoader
import com.coradec.apps.trader.ctrl.ContractProcessor
import com.coradec.apps.trader.ctrl.ContractUpdater
import com.coradec.apps.trader.ibkr.ctrl.InteractiveBroker
import com.coradec.apps.trader.ibkr.ctrl.SwitchBoard
import com.coradec.apps.trader.model.DatabaseParameters
import com.coradec.coradeck.bus.model.impl.BasicBusApplication
import com.coradec.coradeck.bus.module.CoraBus
import com.coradec.coradeck.bus.module.CoraBusImpl
import com.coradec.coradeck.com.model.impl.BasicRequest
import com.coradec.coradeck.com.model.impl.Syslog
import com.coradec.coradeck.com.module.CoraComImpl
import com.coradec.coradeck.conf.model.LocalProperty
import com.coradec.coradeck.conf.module.CoraConfImpl
import com.coradec.coradeck.core.model.Origin
import com.coradec.coradeck.core.model.Timespan
import com.coradec.coradeck.ctrl.module.CoraControl
import com.coradec.coradeck.ctrl.module.CoraControlImpl
import com.coradec.coradeck.db.model.Database
import com.coradec.coradeck.db.module.CoraDB
import com.coradec.coradeck.db.module.CoraDbHsql
import com.coradec.coradeck.dir.module.CoraDirImpl
import com.coradec.coradeck.module.model.CoraModules
import com.coradec.coradeck.text.module.CoraTextImpl
import com.coradec.coradeck.type.model.Password
import com.coradec.coradeck.type.module.impl.CoraTypeImpl
import java.net.URI
import java.nio.file.Paths
import java.util.concurrent.TimeUnit.SECONDS
import javax.swing.JOptionPane.OK_OPTION
import javax.swing.JOptionPane.showMessageDialog
import kotlin.io.path.exists
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

class StartCommand(origin: Origin, val commandLineArguments: List<String>) : BasicRequest(origin)

class Trader(args: List<String>) : BasicBusApplication("Trader", args) {
    val db: Database = CoraDB.database(dbURI, dbUserName, dbPassword)
    private val contractLoader = ContractLoader(db)
    private val contractProcessor = ContractProcessor(db)
    private val contractUpdater = ContractUpdater(db)
    private val accountManager = AccountManager(db)

    override fun onInitializing() {
        super.onInitializing()
        val dbMember = db.memberView
        add("TradeDB", dbMember)
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
        add("AccountManager", accountManager.memberView).standby()
        add("ContractLoader", contractLoader.memberView)
        add("ContractProcessor", contractProcessor.memberView)
        add("ContractUpdater", contractUpdater.memberView)
        CoraBus.machineBus.add("InteractiveBroker", InteractiveBroker.memberView)
    }

    override fun run() {
        debug("Starting Trader.")
        showMessageDialog(null, "Click OK to finish Trader.", "Termination Dialog", OK_OPTION)
        IMMEX.standby(Timespan(5, SECONDS))
        debug("Accounts: %s", SwitchBoard.accounts.value)
        detach()
    }

    companion object {
        private val IMMEX = CoraControl.IMMEX
        private val PROP_DB_URI = LocalProperty<DatabaseParameters>("DB")
        private val dbURI: URI get() = PROP_DB_URI.value.URI
        private val dbUserName: String get() = PROP_DB_URI.value.Username
        private val dbPassword: Password get() = PROP_DB_URI.value.Password
    }
}
