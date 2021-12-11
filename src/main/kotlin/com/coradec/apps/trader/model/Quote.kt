package com.coradec.apps.trader.model

interface Quote {
    val open: Double
    val high: Double
    val low: Double
    val close: Double
    val volume: Long
    val weightedAveragePrice: Double
    val count: Int
}
