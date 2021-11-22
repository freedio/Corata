package com.coradec.apps.trader.ibkr.com.event

import com.coradec.apps.trader.ibkr.com.event.BasicOrderEvent
import com.coradec.coradeck.core.model.Origin
import com.ib.client.Contract
import com.ib.client.Order
import com.ib.client.OrderState

class OpenOrderEvent(origin: Origin, orderId: Int, val contract: Contract, val order: Order, val orderState: OrderState) :
    BasicOrderEvent(origin, orderId)
