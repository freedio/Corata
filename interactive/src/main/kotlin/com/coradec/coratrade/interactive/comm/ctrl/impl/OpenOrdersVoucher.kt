/*
 * Copyright © 2019 by Coradec LLC.  All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.ctrl.impl

import com.coradec.coradeck.com.model.Information
import com.coradec.coradeck.core.ctrl.Origin
import com.coradec.coradeck.type.model.GenericType
import com.coradec.coratrade.interactive.comm.model.impl.EndOpenOrdersEvent
import com.coradec.coratrade.interactive.comm.model.impl.OpenOrderEvent
import com.coradec.coratrade.interactive.ctrl.RequestDispatcher
import com.coradec.coratrade.interactive.model.ContractedOrder

@Suppress("UNCHECKED_CAST")
class OpenOrdersVoucher(origin: Origin, private val all: Boolean = true) :
        RequestDispatcher.IBrokerVoucher<Map<Int, ContractedOrder>>(
                GenericType.of(Map::class.java, Int::class.java, ContractedOrder::class.java)
                        as GenericType<Map<Int, ContractedOrder>>, origin) {
    override val interests = setOf(OpenOrderEvent::class.java, EndOpenOrdersEvent::class.java)
    private val orders = mutableMapOf<Int, ContractedOrder>()

    override fun trigger() {
        RequestDispatcher.requestOpenOrders(this, all)
    }

    override fun notify(info: Information): Boolean {
        var finished = false
        when (info) {
            is EndOpenOrdersEvent -> {
                value = orders
                succeed()
                finished = true
            }
            is OpenOrderEvent -> orders[info.orderId] = info.contractedOrder
            else -> finished = super.notify(info)
        }
        return finished
    }
}
