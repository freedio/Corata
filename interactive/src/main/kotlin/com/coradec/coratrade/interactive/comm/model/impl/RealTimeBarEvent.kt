/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.model.impl

import com.coradec.coradeck.core.ctrl.Origin
import com.coradec.coratrade.interactive.comm.model.impl.BasicResponseEvent
import com.coradec.coratrade.interactive.model.RealtimeBar
import java.time.LocalDateTime.now

class RealTimeBarEvent(origin: Origin, requestId: Int, val realtimeBar: RealtimeBar):
        BasicResponseEvent(origin, requestId, now())
