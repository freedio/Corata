package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin

class AccountUpdateEvent(
    origin: Origin, requestId: Int, val account: String, val modelCode: String, val key: String, val value: String,
    val currency: String?
): BasicRequestEvent(origin, requestId)
