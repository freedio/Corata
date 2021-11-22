package com.coradec.apps.trader.model

import com.coradec.apps.trader.model.impl.BasicStampedQuantifiedQuote
import java.time.ZonedDateTime

interface StampedQuantifiedQuote {
    val timestamp: ZonedDateTime
    val quote: QuantifiedQuote

    companion object {
        operator fun invoke(timestamp: ZonedDateTime, quote: QuantifiedQuote): StampedQuantifiedQuote =
            BasicStampedQuantifiedQuote(timestamp, quote)
    }
}
