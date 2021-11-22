package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin
import com.ib.client.HistoricalTick

class HistoricalTicksEvent(origin: Origin, requestId: Int, val ticks: List<HistoricalTick>, val done: Boolean) :
    BasicRequestEvent(origin, requestId)
