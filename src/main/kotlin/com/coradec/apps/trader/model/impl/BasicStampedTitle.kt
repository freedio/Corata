package com.coradec.apps.trader.model.impl

import com.coradec.apps.trader.model.StampedTitle
import com.coradec.apps.trader.model.Title
import java.time.ZonedDateTime

data class BasicStampedTitle(override val title: Title, override val timestamp: ZonedDateTime) : StampedTitle
