/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model.impl

import com.ib.client.Contract

class MultiPosition(
        contract: Contract,
        positions: Double,
        avgCost: Double,
        val modelCode: String
): BasicPosition(contract, positions, avgCost)
