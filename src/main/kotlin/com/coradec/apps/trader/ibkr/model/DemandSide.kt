package com.coradec.apps.trader.ibkr.model

enum class DemandSide {
    ASK, BID;

    companion object {
        operator fun invoke(side: Int): DemandSide = values().singleOrNull { it.ordinal == side }
            ?: throw IllegalArgumentException("Invalid demand side code: $side!")
    }
}
