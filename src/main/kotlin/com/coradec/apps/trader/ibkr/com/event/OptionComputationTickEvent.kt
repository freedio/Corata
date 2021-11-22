package com.coradec.apps.trader.ibkr.com.event

import com.coradec.apps.trader.ibkr.com.event.FieldTickEvent
import com.coradec.apps.trader.ibkr.model.TickBase
import com.coradec.coradeck.core.model.Origin

class OptionComputationTickEvent(
    origin: Origin, tickerId: Int, field: Int, val tickAttrib: TickBase, val impliedVolatility: Double, val delta: Double,
    val optionPrice: Double, val pvDividend: Double, val gamma: Double, val vega: Double, val theta: Double, val underPrice: Double
) : FieldTickEvent(origin, tickerId, field)
