package com.coradec.apps.trader.com.impl

import com.coradec.apps.trader.model.QuoteType
import com.coradec.apps.trader.model.Title
import com.coradec.apps.trader.model.TitleDataRequest
import com.coradec.coradeck.com.model.impl.BasicRequest
import com.coradec.coradeck.core.model.Origin
import com.coradec.coradeck.core.model.Priority.B3
import com.coradec.coradeck.core.util.asLocalDate
import java.time.LocalDate
import java.time.LocalDateTime

class TitleDataRequestDaily(
    origin: Origin,
    override val title: Title,
    private val dates: MutableSet<LocalDate>,
    override val type: QuoteType
) : BasicRequest(origin, priority = B3), TitleDataRequest {
    override val capacity = dates.size
    val first: LocalDate = dates.first()
    val last: LocalDate = dates.last().plusDays(1)

    override fun contains(barstamp: LocalDateTime): Boolean = barstamp.toLocalDate() in dates
    override fun parse(time: String): LocalDateTime = time.asLocalDate("yyyyMMdd").atStartOfDay()
}
