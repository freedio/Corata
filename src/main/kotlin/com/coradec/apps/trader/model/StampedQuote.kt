package com.coradec.apps.trader.model

import com.coradec.apps.trader.model.impl.BasicStampedQuote
import java.time.ZonedDateTime

interface StampedQuote {
    val timestamp: ZonedDateTime
    val quote: Quote

    companion object {
        operator fun invoke(timestamp: ZonedDateTime, quote: Quote): StampedQuote =
            BasicStampedQuote(timestamp, quote)
    }
}
