package com.coradec.apps.trader.model.impl

import com.coradec.apps.trader.model.Quote

open class BasicQuote(
    override val open: Double,
    override val high: Double,
    override val low: Double,
    override val close: Double
) : Quote
