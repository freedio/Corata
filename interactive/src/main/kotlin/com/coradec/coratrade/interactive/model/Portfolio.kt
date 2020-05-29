/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model

import com.coradec.coratrade.interactive.model.impl.BasicPortfolio
import com.ib.client.Contract

interface Portfolio {
    companion object {
        fun of(
                contract: Contract,
                position: Double,
                marketPrice: Double,
                marketValue: Double,
                averageCost: Double,
                unrealizedPNL: Double,
                realizedPNL: Double
        ) = BasicPortfolio(contract, position, marketPrice, marketValue, averageCost, unrealizedPNL, realizedPNL)
    }
}
