package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin
import com.coradec.coradeck.core.model.Priority
import com.coradec.coradeck.core.model.Priority.Companion.defaultPriority

open class BasicRequestEvent(origin: Origin, val requestId: Int, priority: Priority = defaultPriority): InteractiveEvent(origin, priority)
