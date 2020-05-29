/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model

import com.coradec.coratrade.interactive.model.impl.BasicContractedOrder
import com.ib.client.Contract
import com.ib.client.Order
import com.ib.client.OrderState

interface ContractedOrder {
    companion object {
        fun of(contract: Contract, order: Order, orderState: OrderState) = BasicContractedOrder(contract, order, orderState)
    }
}
