package com.coradec.apps.trader.model

import java.time.ZonedDateTime

interface FullQuote {
    val titleRef: String
    val timestamp: ZonedDateTime
    val frequency: Frequency
    val type: QuoteType
    val open: Double
    val high: Double
    val low: Double
    val close: Double
    val volume: Long?
    val weightedAveragePrice: Double?
    val count: Long?
}
