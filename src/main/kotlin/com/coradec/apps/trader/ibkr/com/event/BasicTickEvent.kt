package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin
import com.coradec.coradeck.core.model.Priority
import com.coradec.coradeck.core.model.Priority.Companion.defaultPriority

open class BasicTickEvent(
    origin: Origin,
    val tickerId: Int,
    priority: Priority = defaultPriority
): InteractiveEvent(origin, priority)
