/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.model.impl

import com.coradec.coradeck.core.ctrl.Origin
import com.coradec.coratrade.interactive.comm.model.impl.BasicResponseEvent
import java.time.LocalDateTime.now

class HeadTimestampEvent(origin: Origin, requestId: Int, val headTimestamp: String):
        BasicResponseEvent(origin, requestId, now())
