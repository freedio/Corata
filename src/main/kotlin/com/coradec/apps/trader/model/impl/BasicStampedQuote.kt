package com.coradec.apps.trader.model.impl

import com.coradec.apps.trader.model.Quote
import com.coradec.apps.trader.model.StampedQuote
import java.time.ZonedDateTime

data class BasicStampedQuote(
    override val timestamp: ZonedDateTime,
    override val quote: Quote
) : StampedQuote
