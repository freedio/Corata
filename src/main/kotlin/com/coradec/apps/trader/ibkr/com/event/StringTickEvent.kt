package com.coradec.apps.trader.ibkr.com.event

import com.coradec.apps.trader.ibkr.com.event.FieldTickEvent
import com.coradec.coradeck.core.model.Origin

class StringTickEvent(origin: Origin, tickerId: Int, field: Int, val value: String) :
    FieldTickEvent(origin, tickerId, field)
