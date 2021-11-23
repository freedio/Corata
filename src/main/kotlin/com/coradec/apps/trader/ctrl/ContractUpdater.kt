package com.coradec.apps.trader.ctrl

import com.coradec.apps.trader.model.DbQuote
import com.coradec.apps.trader.model.Title
import com.coradec.coradeck.bus.model.impl.BasicBusNode
import com.coradec.coradeck.db.model.Database
import com.coradec.coradeck.db.model.RecordTable

class ContractUpdater(val db: Database) : BasicBusNode() {
    private lateinit var dayQuotes: RecordTable<DbQuote>
    private lateinit var titles: RecordTable<Title>

    override fun onInitializing() {
        super.onInitializing()
//        titles = db.getTable(BasicTitle::class)
//        dayQuotes = db.openTable(BasicDbQuote::class)
    }

    override fun onFinalized() {
//        titles.close()
//        dayQuotes.close()
        super.onFinalized()
    }
}
