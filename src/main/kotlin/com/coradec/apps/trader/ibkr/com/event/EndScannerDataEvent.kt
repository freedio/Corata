package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin

class EndScannerDataEvent(origin: Origin, requestId: Int): BasicScannerEvent(origin, requestId)
