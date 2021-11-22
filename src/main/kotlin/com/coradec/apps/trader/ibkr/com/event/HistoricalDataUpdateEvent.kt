package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin
import com.ib.client.Bar

class HistoricalDataUpdateEvent(origin: Origin, requestId: Int, bar: Bar): BasicRequestEvent(origin, requestId)
