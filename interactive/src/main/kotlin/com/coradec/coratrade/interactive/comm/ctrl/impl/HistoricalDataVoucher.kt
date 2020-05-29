/*
 * Copyright © 2019 by Coradec LLC.  All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.ctrl.impl

import com.coradec.coradeck.com.model.Information
import com.coradec.coradeck.core.ctrl.Origin
import com.coradec.coradeck.type.model.GenericType
import com.coradec.coratrade.interactive.comm.model.impl.EndHistoricalDataEvent
import com.coradec.coratrade.interactive.comm.model.impl.HistoricalDataEvent
import com.coradec.coratrade.interactive.ctrl.RequestDispatcher
import com.coradec.coratrade.interactive.model.HistoryDataSelection
import com.ib.client.Bar
import com.ib.client.Contract

@Suppress("UNCHECKED_CAST")
class HistoricalDataVoucher(origin: Origin, val contract: Contract, private val selection: HistoryDataSelection) :
        RequestDispatcher.IBrokerRequestVoucher<List<Bar>>(
                GenericType.of(List::class.java, Bar::class.java)
                        as GenericType<List<Bar>>, origin) {
    override val interests = setOf(HistoricalDataEvent::class.java, EndHistoricalDataEvent::class.java)
    private val bars = mutableListOf<Bar>()

    override fun trigger() {
        RequestDispatcher.requestHistoricalData(this, requestId, contract, selection)
    }

    override fun notify(info: Information): Boolean {
        var finished = false
        when (info) {
            is EndHistoricalDataEvent -> {
                value = bars
                succeed()
                finished = true
            }
            is HistoricalDataEvent -> bars += info.bar
            else -> finished = super.notify(info)
        }
        return finished
    }
}
