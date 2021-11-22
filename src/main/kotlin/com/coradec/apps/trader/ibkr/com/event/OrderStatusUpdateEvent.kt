package com.coradec.apps.trader.ibkr.com.event

import com.coradec.apps.trader.ibkr.com.event.BasicOrderEvent
import com.coradec.coradeck.core.model.Origin
import com.ib.client.OrderStatus

class OrderStatusUpdateEvent(
    origin: Origin, orderId: Int, val status: OrderStatus, val filled: Long, val remaining: Long, val averageFillPrice: Double,
    val permId: Int, val parentId: Int, val lastFillPrice: Double, val clientId: Int, val holdingReason: String,
    val marketCapPrice: Double
) : BasicOrderEvent(origin, orderId)
