/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.model.trouble.impl

import com.coradec.coradeck.com.model.impl.BasicEvent
import com.coradec.coradeck.core.ctrl.Origin
import com.coradec.coratrade.interactive.comm.model.trouble.ErrorEvent
import java.time.LocalDateTime.now

class ApiErrorEvent(origin: Origin, val exception: Exception? = null, val message: String? = null):
        BasicEvent(origin, now()), ErrorEvent {
    override val errorCode: Int = -1
    override val errorMessage: String = "ApiError"
}
