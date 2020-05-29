/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.model.impl

import com.coradec.coradeck.core.ctrl.Origin
import java.time.LocalDateTime.now

class EndHistoricalNewsEvent(origin: Origin, requestId: Int, val moreAvailable: Boolean):
        BasicResponseEvent(origin, requestId, now())
