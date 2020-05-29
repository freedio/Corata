/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model

import com.coradec.coratrade.interactive.model.impl.Level1MarketDepthUpdate
import com.coradec.coratrade.interactive.model.impl.Level2MarketDepthUpdate

interface MarketDepthUpdate {
    companion object {
        fun of(position: Int, operation: Int, side: Int, price: Double, size: Int) =
                Level1MarketDepthUpdate(position, operation, side, price, size)

        fun of(marketMaker: String, position: Int, operation: Int, side: Int, price: Double, size: Int, smartDepth: Boolean) =
                Level2MarketDepthUpdate(marketMaker, position, operation, side, price, size, smartDepth)
    }
}
