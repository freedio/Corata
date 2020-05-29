/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model

import com.coradec.coratrade.interactive.model.impl.BasicMidPointTick
import java.time.ZonedDateTime

interface MidPointTick: Tick {

    companion object {
        fun of(systime: ZonedDateTime, midPoint: Double): MidPointTick = BasicMidPointTick(systime, midPoint)
    }

}
