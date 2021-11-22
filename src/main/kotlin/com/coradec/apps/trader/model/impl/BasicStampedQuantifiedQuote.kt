package com.coradec.apps.trader.model.impl

import com.coradec.apps.trader.model.QuantifiedQuote
import com.coradec.apps.trader.model.StampedQuantifiedQuote
import java.time.ZonedDateTime

data class BasicStampedQuantifiedQuote(
    override val timestamp: ZonedDateTime,
    override val quote: QuantifiedQuote
) : StampedQuantifiedQuote
