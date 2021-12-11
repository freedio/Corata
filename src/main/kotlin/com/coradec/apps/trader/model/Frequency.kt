package com.coradec.apps.trader.model

import com.coradec.coradeck.core.model.SqlTransformable

enum class Frequency(val label: String, val labelly: String) : SqlTransformable {
    s30("30 seconds", "half-minutely"),
    m1("1 minute", "minutely"),
    m5("5 minutes", "5-minutely"),
    m15("15 minutes", "quarter-hourly"),
    m30("30 minutes", "half-hourly"),
    h1("1 hour", "hourly"),
    h2("2 hours", "bi-hourly"),
    h4("4 hours", "4-hourly"),
    D1("1 day", "daily"),
    W1("1 week", "weekly"),
    M1("1 month", "monthly");

    override fun toSqlValue() = ordinal.toString()

    companion object {
        val sqlType = "TINYINT"
        fun fromSql(value: Int) = values().single { it.ordinal == value }
    }
}
