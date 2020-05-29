/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.model.impl

import com.coradec.coradeck.com.model.impl.BasicEvent
import com.coradec.coradeck.core.ctrl.Origin
import com.ib.client.HistoricalTickLast
import java.time.LocalDateTime.now

class HistoricalLastTicksEvent(origin: Origin, val requestId: Int, val ticks: List<HistoricalTickLast>?):
        BasicEvent(origin, now())
