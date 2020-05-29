/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model

import com.coradec.coratrade.interactive.model.impl.BasicPosition
import com.coradec.coratrade.interactive.model.impl.MultiPosition
import com.ib.client.Contract

interface Position {
    companion object {
        fun of(contract: Contract, positions: Double, avgCost: Double): Position = BasicPosition(contract, positions, avgCost)
        fun of(contract: Contract, positions: Double, avgCost: Double, modelCode: String) =
                MultiPosition(contract, positions, avgCost, modelCode)
    }
}
