/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.model.impl

import com.coradec.coradeck.com.model.impl.BasicEvent
import com.coradec.coradeck.core.ctrl.Origin
import com.coradec.coratrade.interactive.model.ContractedOrder
import java.time.LocalDateTime.now

class OpenOrderEvent(origin: Origin, val orderId: Int, val contractedOrder: ContractedOrder): BasicEvent(origin, now())
