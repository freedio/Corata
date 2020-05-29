/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model.impl

import com.coradec.coratrade.interactive.model.ProfitAndLoss

class BasicProfitAndLoss(
        val dailyPnL: Double,
        val unrealizedPnL: Double,
        val realizedPnL: Double
) : ProfitAndLoss {

}
