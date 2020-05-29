/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.model.impl

import com.coradec.coradeck.core.ctrl.Origin
import com.ib.client.ContractDetails
import java.time.LocalDateTime.now

class BondContractDetailsEvent(origin: Origin, requestId: Int, val contractDetails: ContractDetails):
        BasicResponseEvent(origin, requestId, now())
