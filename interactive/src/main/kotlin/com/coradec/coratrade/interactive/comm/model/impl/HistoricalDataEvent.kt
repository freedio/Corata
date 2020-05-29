/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.model.impl

import com.coradec.coratrade.interactive.ctrl.ResponseDispatcher
import com.ib.client.Bar
import java.time.LocalDateTime.now

class HistoricalDataEvent(origin: ResponseDispatcher, requestId: Int, val bar: Bar): BasicResponseEvent(origin, requestId, now())
