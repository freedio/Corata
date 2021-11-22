package com.coradec.apps.trader.ibkr.com

import com.coradec.apps.trader.ibkr.com.event.InteractiveEvent
import com.coradec.apps.trader.ibkr.trouble.IllegalEventTypeException
import com.coradec.coradeck.com.model.*
import com.coradec.coradeck.com.model.impl.BasicVoucher
import com.coradec.coradeck.core.model.Origin
import com.coradec.coradeck.ctrl.ctrl.IMMEX
import com.coradec.coradeck.ctrl.module.CoraControl
import com.ib.client.EClientSocket
import java.util.concurrent.atomic.AtomicInteger

abstract class InteractiveVoucher<T>(origin: Origin) : BasicVoucher<T>(origin), Recipient {
    protected val requestId: Int get() = REQID.getAndIncrement()
    abstract fun request(socket: EClientSocket)
    abstract fun listen(producer: IMMEX)
    abstract fun process(event: InteractiveEvent): Boolean
    override fun <I : Information> accept(info: I): Message<I> = producer.inject(info unto this)
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

    override fun process() {
        listen(producer)
        super.process()
    }

    companion object {
        private val REQID = AtomicInteger(0)
        private val producer = CoraControl.IMMEX
    }
}
