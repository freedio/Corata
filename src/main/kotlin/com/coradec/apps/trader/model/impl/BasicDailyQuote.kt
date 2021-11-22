package com.coradec.apps.trader.model.impl

import com.coradec.apps.trader.model.DailyQuote
import com.coradec.apps.trader.model.QuoteType
import com.coradec.apps.trader.model.Title
import java.time.LocalDate

data class BasicDailyQuote(
    override val title: Title,
    override val daystamp: LocalDate,
    override val type: QuoteType,
    override val open: Double,
    override val high: Double,
    override val low: Double,
    override val close: Double,
    override val volume: Long? = null,
    override val weightedAveragePrice: Double? = null,
    override val count: Int? = null
) : DailyQuote
