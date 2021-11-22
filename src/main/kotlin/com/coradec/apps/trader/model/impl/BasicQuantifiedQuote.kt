package com.coradec.apps.trader.model.impl

import com.coradec.apps.trader.model.QuantifiedQuote

data class BasicQuantifiedQuote(
    override val open: Double,
    override val high: Double,
    override val low: Double,
    override val close: Double,
    override val volume: Long,
    override val weightedAveragePrice: Double,
    override val count: Int,
) : QuantifiedQuote
