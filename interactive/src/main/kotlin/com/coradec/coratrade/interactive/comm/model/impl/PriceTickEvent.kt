/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.model.impl

import com.coradec.coradeck.com.model.impl.BasicEvent
import com.coradec.coratrade.interactive.ctrl.ResponseDispatcher
import com.coradec.coratrade.interactive.model.PriceTick
import java.time.LocalDateTime.now

class PriceTickEvent(origin: ResponseDispatcher, val tickerId: Int, val tick: PriceTick) : BasicEvent(origin, now())
