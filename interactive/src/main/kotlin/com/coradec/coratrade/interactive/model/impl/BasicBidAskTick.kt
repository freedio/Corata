/*
 * Copyright © 2019 by Coradec LLC.  All rights reserved.
 */

package com.coradec.coratrade.interactive.model.impl

import com.coradec.coratrade.interactive.model.BidAskTick
import com.ib.client.TickAttribBidAsk
import java.time.ZonedDateTime

class BasicBidAskTick(
        val timestamp: ZonedDateTime,
        val bidPrice: Double,
        val bidSize: Int,
        val askPrice: Double,
        val askSize: Int,
        val tickAttribBidAsk: TickAttribBidAsk
) : BidAskTick
