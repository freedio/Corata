/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.model.impl

import com.coradec.coradeck.com.model.impl.BasicEvent
import com.coradec.coradeck.core.ctrl.Origin
import com.coradec.coratrade.interactive.model.impl.BasicPortfolio
import java.time.LocalDateTime.now

class PortfolioUpdateEvent(origin: Origin, val accountName: String, val portfolio: BasicPortfolio): BasicEvent(origin, now())
