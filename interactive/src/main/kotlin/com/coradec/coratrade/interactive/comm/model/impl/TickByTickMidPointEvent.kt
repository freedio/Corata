/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.model.impl

import com.coradec.coratrade.interactive.ctrl.ResponseDispatcher
import com.coradec.coratrade.interactive.model.MidPointTick
import java.time.LocalDateTime.now

class TickByTickMidPointEvent(origin: ResponseDispatcher, requestId: Int, val tick: MidPointTick):
        BasicResponseEvent(origin, requestId, now())
