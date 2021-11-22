package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin
import com.ib.client.Contract
import com.ib.client.Order
import com.ib.client.OrderState

class OrderCompletedEvent(origin: Origin, val contract: Contract, val order: Order, val state: OrderState): InteractiveEvent(origin)
