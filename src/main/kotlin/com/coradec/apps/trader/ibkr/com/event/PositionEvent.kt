package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin
import com.ib.client.Contract

class PositionEvent(
    origin: Origin, val account: String, val contract: Contract, val position: Long, val avgCost: Double
): InteractiveEvent(origin)
