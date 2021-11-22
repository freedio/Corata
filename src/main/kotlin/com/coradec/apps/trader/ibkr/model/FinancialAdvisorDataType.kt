package com.coradec.apps.trader.ibkr.model

enum class FinancialAdvisorDataType {
    GROUPS, PROFILES, ALIASES;

    companion object {
        operator fun invoke(faDataType: Int): FinancialAdvisorDataType = values().singleOrNull { it.ordinal == faDataType }
            ?: throw IllegalArgumentException("Invalid financial advisor data type: $faDataType!")
    }
}
