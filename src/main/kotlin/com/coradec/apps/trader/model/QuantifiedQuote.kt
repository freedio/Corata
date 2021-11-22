package com.coradec.apps.trader.model

import com.coradec.apps.trader.model.impl.BasicQuantifiedQuote

interface QuantifiedQuote {
    val open: Double
    val high: Double
    val low: Double
    val close: Double
    val volume: Long
    val weightedAveragePrice: Double
    val count: Int

    companion object {
        operator fun invoke(
            open: Double,
            high: Double,
            low: Double,
            close: Double,
            volume: Long,
            wap: Double,
            count: Int
        ): QuantifiedQuote = BasicQuantifiedQuote(open, high, low, close, volume, wap, count)
    }
}
