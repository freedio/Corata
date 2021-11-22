package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin

class HeadTimestampEvent(origin: Origin, requestId: Int, val headTimestamp: String): BasicRequestEvent(origin, requestId)
