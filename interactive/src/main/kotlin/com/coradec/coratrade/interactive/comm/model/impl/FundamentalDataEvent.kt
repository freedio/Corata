/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.model.impl

import com.coradec.coradeck.core.ctrl.Origin
import com.coradec.coratrade.interactive.comm.model.impl.BasicResponseEvent
import java.time.LocalDateTime.now

class FundamentalDataEvent(origin: Origin, requestId: Int, val data: String): BasicResponseEvent(origin, requestId, now())
