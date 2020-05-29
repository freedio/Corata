/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.model.impl

import com.coradec.coradeck.com.model.impl.BasicEvent
import com.coradec.coradeck.core.ctrl.Origin
import com.coradec.coratrade.interactive.model.AccountSummary
import java.time.LocalDateTime.now

class AccountSummaryEvent(origin: Origin, requestId: Int, val acctSummary: AccountSummary):
        BasicResponseEvent(origin, requestId, now())
