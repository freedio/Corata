package com.coradec.apps.trader.model

import com.coradec.coradeck.core.model.SqlTransformable

enum class TitleState: SqlTransformable {
    /** Currently not interesting. */
    DISABLED,
    /** Currently not traded. */
    INACTIVE,
    /** Actively traded. */
    ACTIVE;

    override fun toSqlValue() = ordinal.toString()

    companion object {
        val sqlType = "TINYINT"
    }
}
