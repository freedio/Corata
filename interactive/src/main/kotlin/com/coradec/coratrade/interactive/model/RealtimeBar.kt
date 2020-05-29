/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model

import com.coradec.coratrade.interactive.model.impl.BasicRealtimeBar
import java.time.ZonedDateTime

interface RealtimeBar {
    companion object {
        fun of(
                timestamp: ZonedDateTime?,
                open: Double,
                high: Double,
                low: Double,
                close: Double,
                volume: Long,
                wap: Double,
                count: Int
        ): RealtimeBar = BasicRealtimeBar(timestamp, open, high, low, close, volume, wap, count)
    }
}
