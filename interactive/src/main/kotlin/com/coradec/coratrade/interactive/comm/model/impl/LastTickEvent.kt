/*
 * Copyright © 2019 by Coradec LLC.  All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.model.impl

import com.coradec.coradeck.core.ctrl.Origin
import com.coradec.coratrade.interactive.model.LastTick
import java.time.LocalDateTime.now

class LastTickEvent(
        origin: Origin,
        requestId: Int,
        val tick: LastTick
): BasicResponseEvent(origin, requestId, now())
