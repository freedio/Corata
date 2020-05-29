/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.model.impl

import com.coradec.coradeck.com.model.impl.BasicEvent
import com.coradec.coradeck.core.ctrl.Origin
import java.time.LocalDateTime.now

class AccountValueUpdateEvent(origin: Origin, val accountId: String, val key: String, val value: String, val currency: String?):
        BasicEvent(origin, now())
