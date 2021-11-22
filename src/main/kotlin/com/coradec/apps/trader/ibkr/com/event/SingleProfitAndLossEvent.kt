package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin

class SingleProfitAndLossEvent(
    origin: Origin, requestId: Int, val position: Long, val dailyPnL: Double, val unrealizedPnL: Double, val realizedPnL: Double,
    val value: Double
): BasicRequestEvent(origin, requestId)
