package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin
import com.coradec.coradeck.core.model.Priority
import com.coradec.coradeck.core.model.Priority.Companion.defaultPriority

open class BasicContractDetailsEvent(
    origin: Origin,
    requestId: Int,
    priority: Priority = defaultPriority
): BasicRequestEvent(origin, requestId, priority)
