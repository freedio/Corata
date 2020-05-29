/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.application.ctrl

import com.coradec.coradeck.bus.model.ServiceScope.MACHINE
import com.coradec.coradeck.com.ctrl.impl.Logger
import com.coradec.coradeck.conf.model.Property
import com.coradec.coradeck.data.module.CoraData
import com.coradec.coradeck.dir.module.CoraDir
import com.coradec.coradeck.hsqldb.ctrl.HsqlDbDriver
import com.coradec.coradeck.session.module.CoraSession
import java.nio.file.Paths

object CoraTrade: Logger() {
    private val PROP_DB_LOCATION = Property.define("DatabaseLocation", String::class.java)
    private val PROP_DB_TYPE = Property.define("DatabaseType", String::class.java)
    private val PROP_DB_USERNAME = Property.define("DatabaseUsername", String::class.java)
    private val PROP_DB_PASSWORD = Property.define("DatabasePassword", String::class.java)

    private val session = CoraSession.current
    private val directory = CoraDir.getDirectory(session, "/")

    fun setup() {
        detail("Starting application setup.")
        val dbPath = Paths.get("/tmp/coratrade")
        val driver = HsqlDbDriver("file", dbPath.resolve("db"), "sa", "", scope = MACHINE)
        CoraData.addPersistenceStorage("hsqldb", driver).standby()
        Accounts.preload()
        Orders.preload()
        detail("Application setup finished.")
    }

    fun shutdown() {
        detail("Shutting application down.")
        // ...
        detail("Application shutdown finished.")
    }
}
