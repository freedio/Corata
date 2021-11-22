package com.coradec.apps.trader.ibkr.model

enum class InteractiveMarketDataType {
    REALTIME, FROZEN, DELAYED, DELAYED_FROZEN;

    companion object {
        operator fun invoke(marketDataType: Int): InteractiveMarketDataType = values().singleOrNull { it.ordinal == marketDataType }
            ?: throw IllegalArgumentException("Unknown market data type code: $marketDataType!")
    }
}
