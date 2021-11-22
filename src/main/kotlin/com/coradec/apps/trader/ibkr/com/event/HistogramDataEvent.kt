package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin
import com.ib.client.HistogramEntry

class HistogramDataEvent(origin: Origin, requestId: Int, val data: List<HistogramEntry>): BasicRequestEvent(origin, requestId)
