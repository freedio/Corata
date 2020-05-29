/*
 * Copyright © 2019 by Coradec LLC.  All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.model.impl

import com.coradec.coradeck.core.ctrl.Origin
import com.coradec.coratrade.interactive.model.BidAskTick
import java.time.LocalDateTime.now

class BidAskEvent(origin: Origin, requestId: Int, val tick: BidAskTick): BasicResponseEvent(origin, requestId, now())
