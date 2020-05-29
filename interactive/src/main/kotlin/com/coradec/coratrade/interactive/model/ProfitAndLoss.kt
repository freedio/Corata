/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model

import com.coradec.coratrade.interactive.model.impl.BasicProfitAndLoss
import com.coradec.coratrade.interactive.model.impl.SingleProfitAndLoss

interface ProfitAndLoss {
    companion object {
        fun of(dailyPnL: Double, unrealizedPnL: Double, realizedPnL: Double): ProfitAndLoss =
                BasicProfitAndLoss(dailyPnL, unrealizedPnL, realizedPnL)
        fun of(dailyPnL: Double, unrealizedPnL: Double, realizedPnL: Double, position: Int, value: Double): ProfitAndLoss =
                SingleProfitAndLoss(dailyPnL, unrealizedPnL, realizedPnL, position, value)
    }
}
