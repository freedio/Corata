/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.model.impl

import com.coradec.coradeck.core.ctrl.Origin
import com.coradec.coratrade.interactive.model.impl.MultiPosition
import java.time.LocalDateTime.now

class MultiPositionEvent(origin: Origin, requestId: Int, val position: MultiPosition):
        BasicResponseEvent(origin, requestId, now())
