package com.coradec.apps.trader.model

import java.time.LocalDate

interface DbQuote {
    val titleRef: String
    val daystamp: LocalDate
    val type: QuoteType
    val open: Double
    val high: Double
    val low: Double
    val close: Double
    val volume: Long?
    val weightedAveragePrice: Double?
    val count: Int?
}
