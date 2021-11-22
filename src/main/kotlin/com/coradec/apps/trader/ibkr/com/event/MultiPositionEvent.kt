package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin
import com.ib.client.Contract

class MultiPositionEvent(
    origin: Origin, requestId: Int, val account: String, val contract: Contract, val longValue: Long, val avgCost: Double,
    val modelCode: String
): BasicRequestEvent(origin, requestId)
