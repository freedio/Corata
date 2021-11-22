package com.coradec.apps.trader.model.impl

import com.coradec.apps.trader.model.Frequency
import com.coradec.apps.trader.model.FullQuote
import com.coradec.apps.trader.model.QuoteType
import com.coradec.coradeck.db.annot.Indexed
import com.coradec.coradeck.db.annot.Size
import java.time.ZonedDateTime

data class BasicFullQuote(
    override val titleRef: @Indexed @Size(7) String,
    override val timestamp: @Indexed ZonedDateTime,
    override val frequency: @Indexed Frequency,
    override val type: @Indexed QuoteType,
    override val open: Double,
    override val high: Double,
    override val low: Double,
    override val close: Double,
    override val volume: Long?,
    override val weightedAveragePrice: Double?,
    override val count: Long?
) : FullQuote
