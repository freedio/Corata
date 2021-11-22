package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin
import com.ib.client.HistoricalTickBidAsk

class HistoricalBidAskTicksEvent(
    origin: Origin, requestId: Int, val ticks: List<HistoricalTickBidAsk>, val done: Boolean): BasicRequestEvent(origin, requestId)
