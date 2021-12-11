package com.coradec.coradeck.type.converter

import com.coradec.apps.trader.model.Frequency
import com.coradec.coradeck.type.ctrl.impl.BasicTypeConverter

class com_coradec_apps_trader_model_FrequencyConverter(): BasicTypeConverter<Frequency>(Frequency::class) {
    override fun convertFrom(value: Any): Frequency? = when (value) {
        is Int -> Frequency.values().singleOrNull { it.ordinal == value }
        else -> null
    }

    override fun decodeFrom(value: String): Frequency? =
        Frequency.values().singleOrNull { it.label == value || value in it.labelly.split(',') }
}
