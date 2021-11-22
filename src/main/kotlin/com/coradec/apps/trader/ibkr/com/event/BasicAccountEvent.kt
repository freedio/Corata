package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin

open class BasicAccountEvent(origin: Origin, val accountName: String): InteractiveEvent(origin)
