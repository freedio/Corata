package com.coradec.apps.trader.model

import com.coradec.coradeck.core.model.SqlTransformable

enum class Frequency(val label: String, val moreLabels: String) : SqlTransformable {
    s30("30 seconds", ""),
    m1("1 minute", "minutely"),
    m5("5 minutes", ""),
    m15("15 minutes", ""),
    m30("30 minutes", ""),
    h1("1 hour", "hourly"),
    h2("2 hours", "bi-hourly"),
    h4("4 hours", ""),
    D1("1 day", "daily"),
    W1("1 week", "weekly"),
    M1("1 month", "monthly");

    override fun toSqlValue() = ordinal.toString()

    companion object {
        val sqlType = "TINYINT"
        fun fromSql(value: Int) = values().single { it.ordinal == value }
    }
}
