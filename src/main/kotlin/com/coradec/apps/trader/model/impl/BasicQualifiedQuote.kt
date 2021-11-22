package com.coradec.apps.trader.model.impl

import com.coradec.apps.trader.model.QualifiedQuote
import com.coradec.apps.trader.model.QuoteType
import com.coradec.coradeck.db.annot.Indexed
import com.coradec.coradeck.db.annot.Size
import java.time.ZonedDateTime

class BasicQualifiedQuote(
    override val titleRef: @Indexed @Size(7) String,
    override val timestamp: @Indexed ZonedDateTime,
    override val type: @Indexed QuoteType,
    open: Double,
    high: Double,
    low: Double,
    close: Double,
) : BasicQuote(open,  high, low, close), QualifiedQuote
