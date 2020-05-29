/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.model.trouble.impl

import com.coradec.coradeck.com.model.impl.BasicEvent
import com.coradec.coradeck.core.ctrl.Origin
import com.coradec.coratrade.interactive.comm.model.trouble.ErrorEvent
import java.time.LocalDateTime

class ClientErrorEvent(origin: Origin, override val errorCode: Int, override val errorMessage: String) :
        BasicEvent(origin, LocalDateTime.now()), ErrorEvent
