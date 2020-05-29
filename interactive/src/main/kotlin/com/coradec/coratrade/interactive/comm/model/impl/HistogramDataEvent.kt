/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.model.impl

import com.coradec.coradeck.core.ctrl.Origin
import com.ib.client.HistogramEntry
import java.time.LocalDateTime.now

class HistogramDataEvent(origin: Origin, requestId: Int, val data: MutableList<HistogramEntry>):
        BasicResponseEvent(origin, requestId, now())
