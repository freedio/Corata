/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.model.impl

import com.coradec.coradeck.com.model.impl.BasicEvent
import com.coradec.coratrade.interactive.ctrl.ResponseDispatcher
import com.coradec.coratrade.interactive.model.EFPTick
import java.time.LocalDateTime.now

class EFPTickEvent(origin: ResponseDispatcher, val ticjerId: Int, val efpTick: EFPTick): BasicEvent(origin, now())
