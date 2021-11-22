package com.coradec.apps.trader.ibkr.com.event

import com.coradec.apps.trader.ibkr.com.event.BasicTickEvent
import com.coradec.apps.trader.ibkr.model.DemandSide
import com.coradec.apps.trader.ibkr.model.MarketDepthOperation
import com.coradec.coradeck.core.model.Origin

open class UpdateMarketDepthEvent(
    origin: Origin, tickerId: Int, val position: Int, val operation: MarketDepthOperation, val side: DemandSide, val price: Double,
    val size: Long
) : BasicTickEvent(origin, tickerId)
