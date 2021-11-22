package com.coradec.apps.trader.ibkr.model

enum class MarketDepthOperation {
    INSERT, UPDATE, DELETE;

    companion object {
        operator fun invoke(operation: Int): MarketDepthOperation = values().singleOrNull { it.ordinal == operation }
            ?: throw IllegalArgumentException("Invalid op code: $operation!")
    }
}
