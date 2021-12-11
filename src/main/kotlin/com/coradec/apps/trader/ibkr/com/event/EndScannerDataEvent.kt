package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin
import com.coradec.coradeck.core.model.Priority.B3

class EndScannerDataEvent(origin: Origin, requestId: Int): BasicScannerEvent(origin, requestId, B3)
