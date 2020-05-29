/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.model.impl

import com.coradec.coratrade.interactive.ctrl.ResponseDispatcher
import java.time.LocalDateTime.now

class EndHistoricalDataEvent(origin: ResponseDispatcher, requestId: Int, val start: String, val end: String):
        BasicResponseEvent(origin, requestId, now())
