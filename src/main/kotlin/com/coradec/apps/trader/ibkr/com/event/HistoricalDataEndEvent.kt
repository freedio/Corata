package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin

class HistoricalDataEndEvent(origin: Origin, requestId: Int, start: String, end: String): BasicRequestEvent(origin, requestId)
