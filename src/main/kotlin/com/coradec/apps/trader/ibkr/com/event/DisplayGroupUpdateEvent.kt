package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin

class DisplayGroupUpdateEvent(origin: Origin, requestId: Int, val contractInfo: String): BasicRequestEvent(origin, requestId)
