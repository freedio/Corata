/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.model.impl

import com.coradec.coradeck.com.model.impl.BasicEvent
import com.coradec.coradeck.core.ctrl.Origin
import com.coradec.coratrade.interactive.model.MarketDepthUpdate
import java.time.LocalDateTime.now

class MarketLevel1DepthUpdateEvent(
        origin: Origin,
        val tickerId: Int,
        val marketDepthUpdate: MarketDepthUpdate
): BasicEvent(origin, now())
