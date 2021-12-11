package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin
import com.coradec.coradeck.core.model.Priority.B3

class HistoricalNewsEndEvent(origin: Origin, requestId: Int, val hasMore: Boolean): BasicRequestEvent(origin, requestId, B3)
