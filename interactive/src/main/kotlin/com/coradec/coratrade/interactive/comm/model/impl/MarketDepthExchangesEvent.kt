/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.model.impl

import com.coradec.coradeck.com.model.impl.BasicEvent
import com.coradec.coradeck.core.ctrl.Origin
import com.ib.client.DepthMktDataDescription
import java.time.LocalDateTime.now

class MarketDepthExchangesEvent(origin: Origin, val depthMktDescrs: List<DepthMktDataDescription>): BasicEvent(origin, now())
