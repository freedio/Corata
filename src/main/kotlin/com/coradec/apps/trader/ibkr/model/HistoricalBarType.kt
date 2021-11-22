package com.coradec.apps.trader.ibkr.model

enum class HistoricalBarType(val code: String) {
    Trades("TRADES"),
    Midpoint("MIDPOINT"),
    Bid("BID"),
    Ask("ASK"),
    BidAsk("BID_ASK"),
    HistoricalVolatility("HISTORICAL_VOLATILITY"),
    OptionImpliedVolatility("OPTION_IMPLIED_VOLATILITY"),
    FeeRate("FEE_RATE"),
    RebateRate("REBATE_RATE");
}
