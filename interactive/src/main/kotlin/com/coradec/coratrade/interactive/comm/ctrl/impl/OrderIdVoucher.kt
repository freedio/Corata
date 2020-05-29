/*
 * Copyright © 2019 by Coradec LLC.  All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.ctrl.impl

import com.coradec.coradeck.com.model.Information
import com.coradec.coradeck.core.ctrl.Origin
import com.coradec.coradeck.type.model.GenericType
import com.coradec.coratrade.interactive.comm.model.impl.NextValidOrderIdEvent
import com.coradec.coratrade.interactive.ctrl.RequestDispatcher

class OrderIdVoucher(origin: Origin) :
        RequestDispatcher.IBrokerVoucher<Int>(GenericType.of(Int::class.java), origin) {
    override val interests: Set<Class<out Information>> = setOf(NextValidOrderIdEvent::class.java)

    override fun trigger() = RequestDispatcher.requestNextOrderId(this)

    override fun notify(info: Information): Boolean =
            if (info is NextValidOrderIdEvent) {
                value = info.orderId
                succeed()
                true
            } else super.notify(info)

}
