package com.coradec.apps.trader.ibkr.com.event

import com.coradec.apps.trader.ibkr.com.event.BasicTickEvent
import com.coradec.coradeck.core.model.Origin
import java.time.LocalDateTime

class NewsTickEvent(
    origin: Origin, tickerId: Int, val timestamp: LocalDateTime, val provider: String, val articleId: String, val headline: String,
    val extraData: String
): BasicTickEvent(origin, tickerId)
