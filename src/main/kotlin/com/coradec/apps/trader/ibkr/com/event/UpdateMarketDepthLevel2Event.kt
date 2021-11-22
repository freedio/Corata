package com.coradec.apps.trader.ibkr.com.event

import com.coradec.apps.trader.ibkr.model.DemandSide
import com.coradec.apps.trader.ibkr.model.MarketDepthOperation
import com.coradec.coradeck.core.model.Origin

class UpdateMarketDepthLevel2Event(
    origin: Origin, tickerId: Int, position: Int, operation: MarketDepthOperation, side: DemandSide, price: Double, size: Long,
    val marketMaker: String, val smartDepth: Boolean
) : UpdateMarketDepthEvent(origin, tickerId, position, operation, side, price, size)
