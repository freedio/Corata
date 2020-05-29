/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model.impl

import com.coradec.coratrade.interactive.model.Position
import com.ib.client.Contract

open class BasicPosition(
        val contract: Contract,
        val positions: Double,
        val avgCost: Double
) : Position {

}
