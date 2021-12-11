package com.coradec.coradeck.type.converter

import com.coradec.apps.trader.model.QuoteType
import com.coradec.coradeck.type.ctrl.impl.BasicTypeConverter

class com_coradec_apps_trader_model_QuoteTypeConverter(): BasicTypeConverter<QuoteType>(QuoteType::class) {
    override fun convertFrom(value: Any): QuoteType? = when (value) {
        is Int -> QuoteType.values().singleOrNull { it.ordinal == value }
        else -> null
    }

    override fun decodeFrom(value: String): QuoteType? =
        QuoteType.values().singleOrNull { value == it.code || value == it.name }
}
