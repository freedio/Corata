package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin
import java.math.BigDecimal
import java.time.LocalDateTime

class RealtimeBarEvent(
    origin: Origin, requestId: Int, val timestamp: LocalDateTime, val open: Double, val high: Double, val low: Double,
    val close: Double, val volume: Long, val wap /*weighted average price*/: BigDecimal, val count: Int
) : BasicRequestEvent(origin, requestId)
