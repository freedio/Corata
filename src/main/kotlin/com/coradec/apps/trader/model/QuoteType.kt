package com.coradec.apps.trader.model

import com.coradec.coradeck.core.model.SqlTransformable

enum class QuoteType(val code: String): SqlTransformable {
    Trades("TRADES"),
    Midpoint("MIDPOINT"),
    Bid("BID"),
    Ask("ASK"),
    BidAsk("BID_ASK"),
    HistoricalVolatility("HISTORICAL_VOLATILITY"),
    OptionImpliedVolatility("OPTION_IMPLIED_VOLATILITY"),
    FeeRate("FEE_RATE"),
    RebateRate("REBATE_RATE");

    override fun toSqlValue(): String = ordinal.toString()

    companion object {
        val sqlType = "TINYINT"
        fun fromSql(value: Int) = QuoteType.values().single { it.ordinal == value }
    }
}
