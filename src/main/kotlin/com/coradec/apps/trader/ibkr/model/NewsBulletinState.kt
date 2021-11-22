package com.coradec.apps.trader.ibkr.model

enum class NewsBulletinState {
    OPEN, CLOSED, OPENING;

    companion object {
        operator fun invoke(msgType: Int): NewsBulletinState = values().singleOrNull { it.ordinal == msgType }
            ?: throw IllegalArgumentException("Invalid news bulletin state: $msgType!")
    }
}
