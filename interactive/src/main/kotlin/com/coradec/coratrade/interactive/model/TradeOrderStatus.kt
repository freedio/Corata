/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model

import com.coradec.coratrade.interactive.model.impl.BasicTradeOrderStatus

interface TradeOrderStatus {
    companion object {
        fun of(
                status: String,
                filled: Double,
                remaining: Double,
                avgFillPrice: Double,
                lastFillPrice: Double,
                holdingReason: String,
                mktCapPrice: Double
        ) = BasicTradeOrderStatus(status, filled, remaining, avgFillPrice, lastFillPrice, holdingReason, mktCapPrice)
    }
}
