package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin

class WSHEventDataEvent(origin: Origin, requestId: Int, eventDataJSON: String): BasicRequestEvent(origin, requestId)
