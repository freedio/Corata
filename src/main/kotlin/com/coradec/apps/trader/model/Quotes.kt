package com.coradec.apps.trader.model

import com.coradec.apps.trader.model.impl.BasicQuotes
import java.time.ZonedDateTime

interface Quotes {
    val title: Title
    val timestamp: ZonedDateTime
    var trades: QuantifiedQuote?
    var midpoints: Quote?
    var bids: Quote?
    var asks: Quote?
    var bidAsks: Quote?
    var historicalVolatilities: Quote?
    var optionImpliedVolatilities: Quote?
    var feeRates: Quote?
    var rebateRates: Quote?

    companion object {
        operator fun invoke(title: Title, timestamp: ZonedDateTime): Quotes = BasicQuotes(title, timestamp)
    }
}
