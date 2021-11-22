package com.coradec.apps.trader.model

import com.coradec.apps.trader.model.impl.BasicQuote

interface Quote {
    val open: Double
    val high: Double
    val low: Double
    val close: Double

    companion object {
        operator fun invoke(
            open: Double,
            high: Double,
            low: Double,
            close: Double
        ): Quote = BasicQuote(open, high, low, close)
    }
}
