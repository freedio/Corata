/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.model

import com.coradec.coratrade.interactive.comm.model.impl.BasicAmount
import java.util.*

interface Amount {
    val magnitude: Double
    val currency: Currency

    companion object {
        fun of(magnitude: Double, currency: Currency): Amount = BasicAmount(magnitude, currency)
    }
}
