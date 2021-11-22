package com.coradec.coradeck.type.converter

import com.coradec.apps.trader.model.SecurityType
import com.coradec.coradeck.type.ctrl.impl.BasicTypeConverter

class com_coradec_apps_trader_model_SecurityTypeConverter: BasicTypeConverter<SecurityType>(SecurityType::class) {
    override fun convertFrom(value: Any): SecurityType? = when (value) {
        is Int -> SecurityType.values().singleOrNull { it.ordinal == value }
        else -> null
    }

    override fun decodeFrom(value: String): SecurityType? =
        SecurityType.values().singleOrNull { it.code == value }
}
