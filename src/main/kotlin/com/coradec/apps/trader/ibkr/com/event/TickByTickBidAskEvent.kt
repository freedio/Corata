package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin
import com.ib.client.TickAttribBidAsk
import java.time.LocalDateTime

class TickByTickBidAskEvent(
    origin: Origin, requestId: Int, val timestamp: LocalDateTime, val bidPrice: Double, val askPrice: Double, val bidsz: Long,
    val asksz: Long, val tickAttrib: TickAttribBidAsk
): BasicRequestEvent(origin, requestId)
