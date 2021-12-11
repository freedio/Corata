package com.coradec.apps.trader.ctrl

import com.coradec.apps.trader.model.DbQuote
import com.coradec.apps.trader.model.Title
import com.coradec.coradeck.bus.model.impl.BasicBusNode
import com.coradec.coradeck.db.model.RecordTable

class ContractProcessor(val contracts: RecordTable<Title>, val dayQuotes: RecordTable<DbQuote>): BasicBusNode() {

}
