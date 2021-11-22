package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin

class FundamentalDataEvent(origin: Origin, requestId: Int, val xmlData: String): BasicRequestEvent(origin, requestId)
