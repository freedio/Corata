package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin

class OrderBoundEvent(origin: Origin, val orderId: Long, val apiClientId: Int, val apiOrderId: Int): InteractiveEvent(origin)
