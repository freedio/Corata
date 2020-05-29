/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model

import com.coradec.coratrade.interactive.model.impl.BasicHistoryDataSelection
import com.ib.client.TagValue
import com.ib.client.Types.BarSize
import com.ib.client.Types.WhatToShow
import java.time.ZonedDateTime

interface HistoryDataSelection {
    val endDateTime: ZonedDateTime
    val duration: DataWindow
    val barSize: BarSize
    val subject: WhatToShow
    val regularTradingHoursOnly: Boolean
    val formattedDates: Boolean
    val continuous: Boolean
    val chartOptions: List<TagValue>

    companion object {
        fun of(
                endDateTime: ZonedDateTime,
                duration: DataWindow,
                barSize: BarSize,
                subject: WhatToShow,
                rth: Boolean = false,
                formatDate: Boolean = false,
                continuous: Boolean = false,
                chartOptions: List<TagValue> = listOf()
        ) = BasicHistoryDataSelection(endDateTime, duration, barSize, subject, rth, formatDate, continuous, chartOptions)
    }
}
