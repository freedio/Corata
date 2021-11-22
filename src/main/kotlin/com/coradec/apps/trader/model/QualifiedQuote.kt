package com.coradec.apps.trader.model

import com.coradec.coradeck.db.annot.Size
import java.time.ZonedDateTime

interface QualifiedQuote: Quote {
    val titleRef: @Size(7) String
    val timestamp: ZonedDateTime
    val type: QuoteType
}
