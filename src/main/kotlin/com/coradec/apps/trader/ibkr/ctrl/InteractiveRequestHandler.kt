package com.coradec.apps.trader.ibkr.ctrl

import com.coradec.apps.trader.com.impl.TitleDataRequestDaily
import com.coradec.apps.trader.ibkr.com.req.HistoricalDataRequestDaily
import com.coradec.coradeck.bus.model.impl.BasicBusNode
import com.coradec.coradeck.com.model.Notification
import com.coradec.coradeck.com.model.RequestState.*
import com.coradec.coradeck.core.util.here
import com.coradec.coradeck.core.util.relax
import com.coradec.coradeck.ctrl.module.CoraControl
import com.coradec.coradeck.ctrl.module.subscribe
import java.util.concurrent.atomic.AtomicInteger

@Suppress("unused")
class InteractiveRequestHandler : BasicBusNode() {
    private val requests = AtomicInteger(0)

    override fun onInitializing() {
        super.onInitializing()
        subscribe()
        route(TitleDataRequestDaily::class, ::requestHistoryData)
        approve(HistoricalDataRequestDaily::class)
    }

    override fun accepts(notification: Notification<*>) = when (notification.content) {
        is TitleDataRequestDaily, is HistoricalDataRequestDaily -> true
        else -> super.accepts(notification)
    }

    private fun requestHistoryData(req: TitleDataRequestDaily) {
        if (requests.incrementAndGet() > 20) IMMEX.block(this)
        val dailyRequest = HistoricalDataRequestDaily(here, req, req.type)
        debug("Requesting history data ${req.first} − ${req.last} for title ${req.title}.")
        accept(dailyRequest.whenFinished {
            if (requests.decrementAndGet() < 20) IMMEX.release(this@InteractiveRequestHandler)
            when (this.state) {
                SUCCESSFUL -> req.succeed()
                FAILED -> req.fail(reason)
                CANCELLED -> req.cancel(reason)
                else -> relax()
            }
        })
    }

    companion object {
        private val IMMEX = CoraControl.IMMEX
    }
}
