/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.model.impl

import com.coradec.coradeck.core.ctrl.Origin
import com.coradec.coratrade.interactive.comm.model.impl.BasicResponseEvent
import com.ib.client.ContractDescription
import java.time.LocalDateTime.now

class SymbolSamplesEvent(origin: Origin, requestId: Int, val contractDescrs: List<ContractDescription>):
        BasicResponseEvent(origin, requestId, now())
