package com.coradec.apps.trader.ibkr.com

import com.coradec.apps.trader.ibkr.com.event.InteractiveEvent
import com.coradec.apps.trader.ibkr.com.event.TWSCurrentTimeEvent
import com.coradec.apps.trader.ibkr.trouble.IllegalEventTypeException
import com.coradec.coradeck.core.model.Origin
import com.coradec.coradeck.ctrl.ctrl.IMMEX
import com.ib.client.EClientSocket
import java.time.LocalDateTime

class CurrentTimeVoucher(origin: Origin) : InteractiveVoucher<LocalDateTime>(origin) {
    override fun request(socket: EClientSocket) = socket.reqCurrentTime()
    override fun listen(producer: IMMEX) = producer.plugin(TWSCurrentTimeEvent::class, this)
    override fun process(event: InteractiveEvent): Boolean = when (event) {
        is TWSCurrentTimeEvent -> true.also { value = event.timestamp }
        else -> throw IllegalEventTypeException(event)
    }
}
