/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.model.impl

import com.coradec.coradeck.com.model.impl.BasicEvent
import com.coradec.coradeck.core.ctrl.Origin
import com.coradec.coratrade.interactive.comm.model.ResponseEvent
import java.time.LocalDateTime

open class BasicResponseEvent(origin: Origin, val requestId: Int, created: LocalDateTime): BasicEvent(origin, created), ResponseEvent
