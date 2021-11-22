package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin

class FinancialAdvisorReplacedEvent(origin: Origin, requestId: Int, val message: String): BasicRequestEvent(origin, requestId)
