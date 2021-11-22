package com.coradec.apps.trader.ibkr.com.event

import com.coradec.apps.trader.ibkr.com.event.BasicTickEvent
import com.coradec.coradeck.core.model.Origin

class TickRequestParametersEvent(
    origin: Origin, tickerId: Int, val minTick: Double, val BBOExchange: String, val snapshotPermissions: Int
): BasicTickEvent(origin, tickerId)
