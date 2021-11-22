package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin
import com.coradec.coradeck.core.model.Priority
import com.coradec.coradeck.core.model.Priority.Companion.defaultPriority

class AccountSummaryPositionEvent(
    origin: Origin,
    requestId: Int,
    val account: String,
    val tag: String,
    val value: String,
    val currency: String?,
    priority: Priority = defaultPriority
) : BasicRequestEvent(origin, requestId, priority)
