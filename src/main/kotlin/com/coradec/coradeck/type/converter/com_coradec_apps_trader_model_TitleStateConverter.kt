package com.coradec.coradeck.type.converter

import com.coradec.apps.trader.model.TitleState
import com.coradec.coradeck.type.ctrl.impl.BasicTypeConverter

class com_coradec_apps_trader_model_TitleStateConverter(): BasicTypeConverter<TitleState>(TitleState::class) {
    override fun convertFrom(value: Any): TitleState? = when(value) {
        is Int -> TitleState.values().singleOrNull { it.ordinal == value }
        else -> null
    }

    override fun decodeFrom(value: String): TitleState? =
        TitleState.values().singleOrNull { it.name == value }
}
