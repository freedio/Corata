/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.model.impl

import com.coradec.coradeck.core.ctrl.Origin
import com.coradec.coratrade.interactive.model.impl.BasicSecurityDefinitionOptionParameter
import java.time.LocalDateTime.now

class SecurityDefinitionOptionParameterEvent(
        origin: Origin,
        requestId: Int,
        val optionParameter: BasicSecurityDefinitionOptionParameter
): BasicResponseEvent(origin, requestId, now())
