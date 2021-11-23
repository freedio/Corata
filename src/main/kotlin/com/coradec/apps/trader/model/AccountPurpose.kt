package com.coradec.apps.trader.model

import com.coradec.coradeck.core.model.SqlTransformable

enum class AccountPurpose: SqlTransformable {
    PRODUCTIVE, TEST;

    override fun toSqlValue(): String = ordinal.toString()

    companion object {
        val sqlType = "TINYINT"
        fun fromSql(value: Int) = values().single { it.ordinal == value }
    }
}
