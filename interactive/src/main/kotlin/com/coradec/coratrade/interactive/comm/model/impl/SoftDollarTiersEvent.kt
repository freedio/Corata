/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.model.impl

import com.coradec.coradeck.core.ctrl.Origin
import com.coradec.coratrade.interactive.comm.model.impl.BasicResponseEvent
import com.ib.client.SoftDollarTier
import java.time.LocalDateTime.now

class SoftDollarTiersEvent(origin: Origin, requestId: Int, val refined: List<SoftDollarTier>):
        BasicResponseEvent(origin, requestId, now())
