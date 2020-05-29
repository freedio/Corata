/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.model.impl

import com.coradec.coradeck.com.model.impl.BasicEvent
import com.coradec.coradeck.core.ctrl.Origin
import com.ib.client.FamilyCode
import java.time.LocalDateTime.now

class FamilyCodeEvent(origin: Origin, val familyCodes: List<FamilyCode>): BasicEvent(origin, now())
