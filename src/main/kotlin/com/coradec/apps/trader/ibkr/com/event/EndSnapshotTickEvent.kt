package com.coradec.apps.trader.ibkr.com.event

import com.coradec.apps.trader.ibkr.com.event.BasicTickEvent
import com.coradec.coradeck.core.model.Origin

class EndSnapshotTickEvent(origin: Origin, tickerId: Int) : BasicTickEvent(origin, tickerId)
