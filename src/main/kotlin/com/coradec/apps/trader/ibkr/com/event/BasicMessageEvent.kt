package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin

open class BasicMessageEvent(origin: Origin, val msgId: Int): InteractiveEvent(origin)
