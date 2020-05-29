/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model.impl

import com.coradec.coratrade.interactive.model.MarketDepthUpdate

class Level2MarketDepthUpdate(
        val marketMaker: String,
        val position: Int,
        val operation: Int,
        val side: Int,
        val price: Double,
        val size: Int,
        val smartDepth: Boolean
): MarketDepthUpdate
