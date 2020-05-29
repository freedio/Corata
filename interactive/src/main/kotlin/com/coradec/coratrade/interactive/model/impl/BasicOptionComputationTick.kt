/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model.impl

import com.coradec.coratrade.interactive.model.OptionComputationTick

class BasicOptionComputationTick(
        val field: Int,
        val impliedVolatility: Double,
        val delta: Double,
        val price: Double,
        val pwDividend: Double,
        val gamma: Double,
        val vega: Double,
        val theta: Double,
        val undPrice: Double
) : OptionComputationTick
