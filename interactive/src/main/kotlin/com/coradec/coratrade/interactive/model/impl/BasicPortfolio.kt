/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model.impl

import com.coradec.coratrade.interactive.model.Portfolio
import com.ib.client.Contract

class BasicPortfolio(
        val contract: Contract,
        val position: Double,
        val marketPrice: Double,
        val marketValue: Double,
        val averageCost: Double,
        val unrealizedPNL: Double,
        val realizedPNL: Double
) : Portfolio
