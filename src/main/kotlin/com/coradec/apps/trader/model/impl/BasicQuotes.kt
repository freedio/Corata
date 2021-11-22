package com.coradec.apps.trader.model.impl

import com.coradec.apps.trader.model.QuantifiedQuote
import com.coradec.apps.trader.model.Quote
import com.coradec.apps.trader.model.Quotes
import com.coradec.apps.trader.model.Title
import java.time.ZonedDateTime

data class BasicQuotes(override val title: Title, override val timestamp: ZonedDateTime) : Quotes {
    override var trades: QuantifiedQuote? = null
    override var midpoints: Quote? = null
    override var bids: Quote? = null
    override var asks: Quote? = null
    override var bidAsks: Quote? = null
    override var historicalVolatilities: Quote? = null
    override var optionImpliedVolatilities: Quote? = null
    override var feeRates: Quote? = null
    override var rebateRates: Quote? = null
}
