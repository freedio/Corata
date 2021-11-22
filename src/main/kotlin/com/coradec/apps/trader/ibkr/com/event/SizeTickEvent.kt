package com.coradec.apps.trader.ibkr.com.event

import com.coradec.apps.trader.ibkr.com.event.FieldTickEvent
import com.coradec.coradeck.core.model.Origin

class SizeTickEvent(origin: Origin, tickerId: Int, field: Int, val size: Long) : FieldTickEvent(origin, tickerId, field)
