/*
 * Copyright © 2019 by Coradec LLC.  All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.ctrl.impl

import com.coradec.coradeck.com.model.Information
import com.coradec.coradeck.core.ctrl.Origin
import com.coradec.coradeck.core.util.relax
import com.coradec.coradeck.type.model.GenericType
import com.coradec.coratrade.interactive.comm.model.impl.ConnectionAcknowledgedEvent
import com.coradec.coratrade.interactive.comm.model.impl.ConnectionClosedEvent
import com.coradec.coratrade.interactive.ctrl.RequestDispatcher

class ConnectionVoucher(origin: Origin) :
        RequestDispatcher.IBrokerVoucher<Boolean>(GenericType.of(Boolean::class.java), origin) {
    override val interests: Set<Class<out Information>> =
            setOf(ConnectionAcknowledgedEvent::class.java, ConnectionClosedEvent::class.java)

    init {
        value = false
        succeed()
    }

    override fun trigger() = relax()

    override fun notify(info: Information): Boolean =
            when (info) {
                is ConnectionAcknowledgedEvent -> {
                    value = true
                    false
                }
                is ConnectionClosedEvent -> {
                    value = false
                    false
                }
                else -> super.notify(info)
            }

}
