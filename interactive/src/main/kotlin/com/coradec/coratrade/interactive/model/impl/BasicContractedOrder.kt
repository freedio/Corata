/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model.impl

import com.coradec.coratrade.interactive.model.ContractedOrder
import com.ib.client.Contract
import com.ib.client.Order
import com.ib.client.OrderState

class BasicContractedOrder(
        val contract: Contract,
        val order: Order,
        val orderState: OrderState
): ContractedOrder
