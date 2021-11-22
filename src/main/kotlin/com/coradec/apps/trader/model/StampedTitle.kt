package com.coradec.apps.trader.model

import com.coradec.apps.trader.model.impl.BasicStampedTitle
import java.time.ZonedDateTime

interface StampedTitle {
    val title: Title
    val timestamp: ZonedDateTime

    companion object {
        operator fun invoke(title: Title, timestamp: ZonedDateTime): StampedTitle = BasicStampedTitle(title, timestamp)
    }
}
