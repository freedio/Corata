package com.coradec.apps.trader.model.impl

import com.coradec.apps.trader.model.DbQuote
import com.coradec.apps.trader.model.QuoteType
import com.coradec.coradeck.db.annot.Indexed
import com.coradec.coradeck.db.annot.Size
import java.time.LocalDateTime

data class BasicDbQuote(
    override val titleRef: @Indexed @Size(7) String,
    override val timeStamp: @Indexed LocalDateTime,
    override val type: @Indexed QuoteType,
    override val open: Double,
    override val high: Double,
    override val low: Double,
    override val close: Double,
    override val volume: Long? = null,
    override val weightedAveragePrice: Double? = null,
    override val count: Int? = null
) : DbQuote
