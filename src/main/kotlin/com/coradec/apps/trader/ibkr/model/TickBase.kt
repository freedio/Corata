package com.coradec.apps.trader.ibkr.model

enum class TickBase {
    PriceBased, ReturnBased;

    companion object {
        operator fun invoke(tickAttrib: Int): TickBase = values().singleOrNull { it.ordinal == tickAttrib + 1 }
            ?: throw IllegalArgumentException("Expected a value between -1 and ${values().size - 2}")
    }
}
