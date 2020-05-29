/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model.impl

import com.coradec.coratrade.interactive.model.DataWindow
import com.coradec.coratrade.interactive.model.HistoryDataSelection
import com.ib.client.TagValue
import com.ib.client.Types
import java.time.ZonedDateTime

class BasicHistoryDataSelection(
        override val endDateTime: ZonedDateTime,
        override val duration: DataWindow,
        override val barSize: Types.BarSize,
        override val subject: Types.WhatToShow,
        override val regularTradingHoursOnly: Boolean,
        override val formattedDates: Boolean,
        override val continuous: Boolean,
        override val chartOptions: List<TagValue>
): HistoryDataSelection
