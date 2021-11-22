package com.coradec.apps.trader.ibkr.model

enum class ArticleType {
    PLAINTEXT, PDF;

    companion object {
        operator fun invoke(articleType: Int): ArticleType = values().singleOrNull { it.ordinal == articleType }
            ?: throw IllegalArgumentException("Invalid article type: $articleType!")
    }
}
