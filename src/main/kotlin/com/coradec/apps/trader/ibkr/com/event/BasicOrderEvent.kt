package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin

open class BasicOrderEvent(origin: Origin, val orderId: Int): InteractiveEvent(origin)
