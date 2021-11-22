package com.coradec.apps.trader.ibkr.com.event

import com.coradec.apps.trader.ibkr.com.event.BasicTickEvent
import com.coradec.coradeck.core.model.Origin

open class FieldTickEvent(origin: Origin, tickerId: Int, val field: Int): BasicTickEvent(origin, tickerId)
