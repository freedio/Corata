package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin
import com.ib.client.TickAttribLast
import com.ib.client.TickType
import java.time.LocalDateTime

class TickByTickAllLastEvent(
    origin: Origin, requestId: Int, val tickType: TickType, val timestamp: LocalDateTime, val price: Double, val size: Long,
    val tickAttribLast: TickAttribLast, val exchange: String, val specialConditions: String
): BasicRequestEvent(origin, requestId)
