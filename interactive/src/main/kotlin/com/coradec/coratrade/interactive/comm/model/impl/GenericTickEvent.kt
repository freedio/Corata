/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.model.impl

import com.coradec.coradeck.com.model.impl.BasicEvent
import com.coradec.coratrade.interactive.ctrl.ResponseDispatcher
import com.coradec.coratrade.interactive.model.GenericTick
import java.time.LocalDateTime.now

class GenericTickEvent(origin: ResponseDispatcher, val tickerId: Int, val tick: GenericTick): BasicEvent(origin, now())
