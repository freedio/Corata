package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin
import com.coradec.coradeck.core.model.Priority.B3

class EndSnapshotTickEvent(origin: Origin, tickerId: Int) : BasicTickEvent(origin, tickerId, B3)
