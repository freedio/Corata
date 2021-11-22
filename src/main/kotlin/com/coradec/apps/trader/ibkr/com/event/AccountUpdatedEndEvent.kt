package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin

class AccountUpdatedEndEvent(origin: Origin, requestId: Int): BasicRequestEvent(origin, requestId)
