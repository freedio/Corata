package com.coradec.coradeck.type.converter

import com.coradec.apps.trader.model.AccountPurpose
import com.coradec.coradeck.type.ctrl.impl.BasicTypeConverter

class com_coradec_apps_trader_model_AccountPurposeConverter: BasicTypeConverter<AccountPurpose>(AccountPurpose::class) {
    override fun convertFrom(value: Any): AccountPurpose? = when (value) {
        is Int -> AccountPurpose.values().singleOrNull { it.ordinal == value }
        else -> null
    }

    override fun decodeFrom(value: String): AccountPurpose? =
        AccountPurpose.values().singleOrNull { it.name == value }
}
