/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.model.impl

import com.coradec.coradeck.com.model.impl.BasicEvent
import com.coradec.coratrade.interactive.ctrl.ResponseDispatcher
import com.coradec.coratrade.interactive.model.RequestParameterTick
import java.time.LocalDateTime.now

class RequestParametersTickEvent(origin: ResponseDispatcher, val tickerId: Int, val tick: RequestParameterTick): BasicEvent(origin, now())
