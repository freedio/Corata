/*
 * Copyright © 2019 by Coradec LLC.  All rights reserved.
 */

package com.coradec.coratrade.interactive.model

import com.coradec.coratrade.interactive.model.impl.BasicBidAskTick
import com.ib.client.TickAttribBidAsk
import java.time.ZonedDateTime

interface BidAskTick : Tick {

    companion object {
        fun of(
                timestamp: ZonedDateTime,
                bidPrice: Double,
                bidSize: Int,
                askPrice: Double,
                askSize: Int,
                tickAttribBidAsk: TickAttribBidAsk
        ): BidAskTick =
                BasicBidAskTick(timestamp, bidPrice, bidSize, askPrice, askSize, tickAttribBidAsk)

    }
}
