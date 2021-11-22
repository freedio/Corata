package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin

class WSHMetaDataEvent(origin: Origin, requestId: Int, metaDataJSON: String): BasicRequestEvent(origin, requestId)
