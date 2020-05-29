/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model

import com.coradec.coratrade.interactive.model.impl.BasicOptionComputationTick

interface OptionComputationTick : Tick {

    companion object {
        fun of(
                field: Int,
                impliedVolatility: Double,
                delta: Double,
                price: Double,
                pwDividend: Double,
                gamma: Double,
                vega: Double,
                theta: Double,
                undPrice: Double
        ): OptionComputationTick =
                BasicOptionComputationTick(field, impliedVolatility, delta, price, pwDividend, gamma, vega, theta, undPrice)
    }

}
