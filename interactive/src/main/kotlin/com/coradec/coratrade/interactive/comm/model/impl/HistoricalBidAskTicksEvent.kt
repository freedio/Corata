/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.model.impl

import com.coradec.coradeck.com.model.impl.BasicEvent
import com.coradec.coradeck.core.ctrl.Origin
import com.ib.client.HistoricalTickBidAsk
import java.time.LocalDateTime
import java.time.LocalDateTime.*

class HistoricalBidAskTicksEvent(origin: Origin, val requestId: Int, val ticks: List<HistoricalTickBidAsk>):
        BasicEvent(origin, now())
