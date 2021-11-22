package com.coradec.apps.trader.ibkr.com.event

import com.coradec.apps.trader.ibkr.com.event.BasicOrderEvent
import com.coradec.coradeck.core.model.Origin

class NextOrderIdEvent(origin: Origin, orderId: Int): BasicOrderEvent(origin, orderId)
