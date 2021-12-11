package com.coradec.apps.trader.ibkr.com

import com.coradec.apps.trader.ibkr.com.event.InteractiveEvent
import com.coradec.apps.trader.ibkr.ctrl.InteractiveBroker
import com.coradec.apps.trader.ibkr.trouble.IllegalEventTypeException
import com.coradec.coradeck.com.model.Notification
import com.coradec.coradeck.com.model.Recipient
import com.coradec.coradeck.com.model.impl.BasicVoucher
import com.coradec.coradeck.core.model.Origin
import com.coradec.coradeck.ctrl.ctrl.IMMEX
import com.ib.client.EClientSocket

abstract class InteractiveVoucher<T>(origin: Origin) : BasicVoucher<T>(origin), Recipient {
    protected val requestId: Int by lazy { InteractiveBroker.next }
    abstract fun request(socket: EClientSocket)
    abstract fun listen(producer: IMMEX)
    abstract fun process(event: InteractiveEvent): Boolean
    override fun receive(notification: Notification<*>) {
        when (val event = notification.content) {
            is InteractiveEvent -> try {
                if (process(event)) succeed()
            } catch (e: Exception) {
                fail(e)
            }
            else -> fail(IllegalEventTypeException(notification))
        }
    }
}
