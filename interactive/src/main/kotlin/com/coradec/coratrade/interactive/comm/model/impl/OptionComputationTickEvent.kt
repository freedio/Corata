/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.model.impl

import com.coradec.coradeck.com.model.impl.BasicEvent
import com.coradec.coratrade.interactive.ctrl.ResponseDispatcher
import com.coradec.coratrade.interactive.model.OptionComputationTick
import java.time.LocalDateTime.now

class OptionComputationTickEvent(origin: ResponseDispatcher, val tickerId: Int, val tick: OptionComputationTick): BasicEvent(origin, now())
