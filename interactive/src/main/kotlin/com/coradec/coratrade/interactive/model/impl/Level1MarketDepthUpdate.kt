/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model.impl

import com.coradec.coratrade.interactive.model.MarketDepthUpdate

class Level1MarketDepthUpdate(
        val position: Int,
        val operation: Int,
        val side: Int,
        val price: Double,
        val size: Int
) : MarketDepthUpdate
