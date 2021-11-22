package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin

class GenericTickEvent(origin: Origin, tickerId: Int, field: Int, val value: Double) : FieldTickEvent(origin, tickerId, field)
