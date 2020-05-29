/*
 * Copyright © 2019 by Coradec LLC.  All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.model.trouble.impl

import com.coradec.coradeck.core.ctrl.Origin
import com.coradec.coratrade.interactive.comm.model.trouble.RequestErrorEvent

class TwsRequestErrorEvent(origin: Origin, override val requestId: Int, errorCode: Int, errorMsg: String) :
        TwsErrorEvent(origin, errorCode, errorMsg), RequestErrorEvent
