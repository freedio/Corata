/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.model.impl

import com.coradec.coradeck.com.model.impl.BasicEvent
import com.coradec.coradeck.core.ctrl.Origin
import com.ib.client.HistoricalTick
import java.time.LocalDateTime.now

class HistoricalTicksEvent(origin: Origin, val requestId: Int, val ticks: List<HistoricalTick>): BasicEvent(origin, now())
