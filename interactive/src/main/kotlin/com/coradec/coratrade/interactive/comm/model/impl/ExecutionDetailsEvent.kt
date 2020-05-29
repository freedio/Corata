/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.model.impl

import com.coradec.coradeck.core.ctrl.Origin
import com.coradec.coratrade.interactive.comm.model.impl.BasicResponseEvent
import com.ib.client.Contract
import com.ib.client.Execution
import java.time.LocalDateTime.now

class ExecutionDetailsEvent(origin: Origin, requestId: Int, val contract: Contract, val execution: Execution):
        BasicResponseEvent(origin, requestId, now())
