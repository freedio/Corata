/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.model.impl

import com.coradec.coradeck.core.ctrl.Origin
import com.coradec.coratrade.interactive.model.AccountModelUpdate
import java.time.LocalDateTime.now

class MultiAccountUpdateEvent(origin: Origin, requestId: Int, val accountModelUpdate: AccountModelUpdate) :
        BasicResponseEvent(origin, requestId, now())
