package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin

class HistoricalNewsEvent(
    origin: Origin, requestId: Int, val time: String, val providerCode: String, val articleId: String, val headline: String
): BasicRequestEvent(origin, requestId)
