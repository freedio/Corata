package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin
import java.time.LocalDateTime

class TickByTickBidMidPointEvent(
    origin: Origin, requestId: Int, val timestamp: LocalDateTime, val price: Double
): BasicRequestEvent(origin, requestId)
