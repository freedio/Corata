package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin

open class BasicHistoryEvent(origin: Origin, val requestId: Int): InteractiveEvent(origin)
