/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model

import com.coradec.coratrade.interactive.model.impl.MarketDataLevel1RequestReroute
import com.coradec.coratrade.interactive.model.impl.MarketDataLevel2RequestReroute

interface MarketDataRequestReroute {
    companion object {
        fun of(conId: Int, exchange: String): MarketDataRequestReroute = MarketDataLevel1RequestReroute(conId, exchange)
        fun of2(conId: Int, exchange: String): MarketDataRequestReroute = MarketDataLevel2RequestReroute(conId, exchange)
    }
}
