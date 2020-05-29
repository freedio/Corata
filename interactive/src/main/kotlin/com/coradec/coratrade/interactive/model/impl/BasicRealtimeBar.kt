/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model.impl

import com.coradec.coratrade.interactive.model.RealtimeBar
import java.time.ZonedDateTime

class BasicRealtimeBar(
        val timestamp: ZonedDateTime?,
        val open: Double,
        val high: Double,
        val low: Double,
        val close: Double,
        val volume: Long,
        val wap: Double,
        val count: Int
) : RealtimeBar {

}
