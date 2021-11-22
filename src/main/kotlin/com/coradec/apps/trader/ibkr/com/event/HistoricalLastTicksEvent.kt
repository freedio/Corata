package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin
import com.ib.client.HistoricalTickLast

class HistoricalLastTicksEvent(origin: Origin, requestId: Int, val ticks: List<HistoricalTickLast>, val done: Boolean) :
    BasicRequestEvent(origin, requestId)
