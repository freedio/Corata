/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.model.impl

import com.coradec.coradeck.com.model.impl.BasicEvent
import com.coradec.coradeck.core.ctrl.Origin
import com.coradec.coratrade.interactive.model.TradeOrderStatus
import java.time.LocalDateTime.now

class OrderStatusEvent(origin: Origin, val orderId: Int, val orderStatus: TradeOrderStatus) : BasicEvent(origin, now())
