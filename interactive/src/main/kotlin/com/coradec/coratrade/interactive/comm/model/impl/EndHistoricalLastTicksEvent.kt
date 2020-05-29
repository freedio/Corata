/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.model.impl

import com.coradec.coradeck.core.ctrl.Origin
import java.time.LocalDateTime.now

class EndHistoricalLastTicksEvent(origin: Origin, requestId: Int): BasicResponseEvent(origin, requestId, now())
