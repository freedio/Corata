package com.coradec.apps.trader.ibkr.ctrl

import com.coradec.apps.trader.com.impl.TitleDataRequestDaily
import com.coradec.apps.trader.ibkr.com.req.HistoricalDataRequestDaily
import com.coradec.coradeck.bus.model.impl.BasicBusNode
import com.coradec.coradeck.core.util.here
import com.coradec.coradeck.ctrl.module.CoraControl.createRequestList
import com.coradec.coradeck.ctrl.module.subscribe

@Suppress("unused")
class InteractiveRequestHandler : BasicBusNode() {
    override fun onInitializing() {
        super.onInitializing()
        subscribe(TitleDataRequestDaily::class)
        route(TitleDataRequestDaily::class, ::requestHistoryData)
    }

    private fun requestHistoryData(voucher: TitleDataRequestDaily) {
        debug("Requesting history data ${voucher.from} − ${voucher.upto} for title ${voucher.title}.")
        accept(
            createRequestList(here, voucher.barTypes.map { HistoricalDataRequestDaily(here, voucher, it) }, InteractiveBroker)
        ) andThen { voucher.seal() }
    }
}
