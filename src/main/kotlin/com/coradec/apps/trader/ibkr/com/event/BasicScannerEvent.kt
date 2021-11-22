package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin

open class BasicScannerEvent(origin: Origin, requestId: Int): BasicRequestEvent(origin, requestId)
