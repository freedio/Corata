package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin

class ProfitAndLossEvent(
    origin: Origin, requestId: Int, val dailyPnL: Double, val unrealizedPnL: Double, val realizedPnL: Double
) : BasicRequestEvent(origin, requestId)
