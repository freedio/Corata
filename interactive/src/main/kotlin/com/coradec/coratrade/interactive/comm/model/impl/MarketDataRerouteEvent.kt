/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.model.impl

import com.coradec.coradeck.core.ctrl.Origin
import com.coradec.coratrade.interactive.model.MarketDataRequestReroute
import java.time.LocalDateTime.now

class MarketDataRerouteEvent(origin: Origin, requestId: Int, val requestReroute: MarketDataRequestReroute):
        BasicResponseEvent(origin, requestId, now())
