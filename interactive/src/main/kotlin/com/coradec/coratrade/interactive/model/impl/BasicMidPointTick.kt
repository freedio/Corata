/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model.impl

import com.coradec.coratrade.interactive.model.MidPointTick
import java.time.ZonedDateTime

class BasicMidPointTick(val systime: ZonedDateTime, val midPoint: Double) : MidPointTick
