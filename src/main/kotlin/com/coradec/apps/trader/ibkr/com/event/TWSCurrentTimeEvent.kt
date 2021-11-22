package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin
import java.time.LocalDateTime

class TWSCurrentTimeEvent(origin: Origin, val timestamp: LocalDateTime) : InteractiveEvent(origin)
