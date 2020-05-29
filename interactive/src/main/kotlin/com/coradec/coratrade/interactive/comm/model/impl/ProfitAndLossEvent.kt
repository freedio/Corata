/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.model.impl

import com.coradec.coradeck.core.ctrl.Origin
import com.coradec.coratrade.interactive.model.ProfitAndLoss
import java.time.LocalDateTime.now

class ProfitAndLossEvent(origin: Origin, requestId: Int, val profitAndLoss: ProfitAndLoss):
        BasicResponseEvent(origin, requestId, now())
