package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin
import com.coradec.coradeck.core.model.Priority

class EndAccountSummaryEvent(origin: Origin, requestId: Int, priority: Priority): BasicRequestEvent(origin, requestId, priority)
