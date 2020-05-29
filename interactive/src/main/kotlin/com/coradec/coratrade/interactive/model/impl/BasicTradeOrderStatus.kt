/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model.impl

import com.coradec.coratrade.interactive.model.TradeOrderStatus

class BasicTradeOrderStatus(
        val status: String,
        val filled: Double,
        val remaining: Double,
        val avgFillPrice: Double,
        val lastFillPrice: Double,
        val holdingReason: String,
        val mktCapPrice: Double
) : TradeOrderStatus {

}
