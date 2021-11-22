package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin

open class ErrorEvent(origin: Origin, val problem: Exception? = null, val message: String? = null): InteractiveEvent(origin)
