package com.coradec.apps.trader.ibkr.com.event

import com.coradec.apps.trader.ibkr.com.event.BasicHistoryEvent
import com.coradec.coradeck.core.model.Origin
import com.ib.client.Bar

class HistoricalDataEvent(origin: Origin, requestId: Int, val bar: Bar): BasicHistoryEvent(origin, requestId)
