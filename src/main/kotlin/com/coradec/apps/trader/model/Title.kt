package com.coradec.apps.trader.model

import java.time.ZonedDateTime
import java.util.*

interface Title {
    val name: String
    val type: SecurityType
    val currency: Currency
    val state: TitleState
    val frequency: Frequency
    val inserted: ZonedDateTime
    val lastUpdated: ZonedDateTime?
    val exchange: String?
    val primExch: String?
}
