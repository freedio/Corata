package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin

class SecurityDefinitionOptionParameterEvent(
    origin: Origin, requestId: Int, val exchange: String, val underlyingId: Int, val tradingClass: String, val multiplier: String,
    val expirations: Set<String>, val strikes: Set<Double>
): BasicRequestEvent(origin, requestId)
