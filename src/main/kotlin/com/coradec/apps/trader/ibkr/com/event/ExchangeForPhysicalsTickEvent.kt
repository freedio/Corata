package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin

class ExchangeForPhysicalsTickEvent(
    origin: Origin, tickerId: Int, field: Int, val basisPoints: Double, val formattedBasisPoints: String, val impliedFuture: Double,
    val holdDays: Int, val futureLastTradeDate: String, val dividendImpact: Double, val dividendsToLastTradeDate: Double
) : FieldTickEvent(origin, tickerId, field)
