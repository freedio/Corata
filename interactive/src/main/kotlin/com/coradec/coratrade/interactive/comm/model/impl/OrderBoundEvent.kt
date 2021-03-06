/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.model.impl

import com.coradec.coradeck.com.model.impl.BasicEvent
import com.coradec.coradeck.core.ctrl.Origin
import java.time.LocalDateTime.now

class OrderBoundEvent(origin: Origin, permOrderId: Long, clientId: Int, orderId: Int): BasicEvent(origin, now())
