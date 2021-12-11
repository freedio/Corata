package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin
import com.coradec.coradeck.core.model.Priority

class HistoricalDataEndEvent(
    origin: Origin,
    requestId: Int,
    val start: String,
    val end: String
): BasicRequestEvent(origin, requestId, Priority.B3)
