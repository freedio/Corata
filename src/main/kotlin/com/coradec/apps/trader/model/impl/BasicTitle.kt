package com.coradec.apps.trader.model.impl

import com.coradec.apps.trader.model.Frequency
import com.coradec.apps.trader.model.SecurityType
import com.coradec.apps.trader.model.Title
import com.coradec.apps.trader.model.TitleState
import com.coradec.coradeck.db.annot.Primary
import com.coradec.coradeck.db.annot.Size
import java.time.ZonedDateTime
import java.util.*

data class BasicTitle(
    override val name: @Primary @Size(7) String,
    override val type: SecurityType,
    override val currency: Currency,
    override val state: TitleState,
    override val frequency: Frequency,
    override val inserted: ZonedDateTime,
    override val lastUpdated: ZonedDateTime?,
    override val exchange: @Size(127) String? = null
): Title
