package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.com.model.impl.BasicEvent
import com.coradec.coradeck.core.model.Origin
import com.coradec.coradeck.core.model.Priority
import com.coradec.coradeck.core.model.Priority.B2

open class InteractiveEvent(origin: Origin, priority: Priority = B2): BasicEvent(origin, priority)
