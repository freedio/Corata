package com.coradec.apps.trader.model

import com.coradec.apps.trader.com.DataRequest
import java.time.LocalDateTime

interface TitleDataRequest : DataRequest {
    /** The title. */
    val title: Title
    /** The type of quote. */
    val type: QuoteType
    /** Expected number of entries. */
    val capacity: Int

    /** Checks if the specified barstamp is contained in the temporal range of the request. */
    operator fun contains(barstamp: LocalDateTime): Boolean
    /** Parses the specified bar time to a local timestamp. */
    fun parse(time: String): LocalDateTime
}
