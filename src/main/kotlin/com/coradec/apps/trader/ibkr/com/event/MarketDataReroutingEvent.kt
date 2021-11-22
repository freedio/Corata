package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin

class MarketDataReroutingEvent(origin: Origin, requestId: Int, val contractId: Int, val exchange: String) :
    BasicRequestEvent(origin, requestId)
