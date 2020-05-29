/*
 * Copyright © 2019 by Coradec LLC.  All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.ctrl.impl

import com.coradec.coradeck.com.model.Information
import com.coradec.coradeck.core.ctrl.Origin
import com.coradec.coradeck.type.model.GenericType
import com.coradec.coratrade.interactive.comm.model.impl.SymbolSamplesEvent
import com.coradec.coratrade.interactive.ctrl.RequestDispatcher
import com.ib.client.ContractDescription

@Suppress("UNCHECKED_CAST")
class SymbolQueryVoucher(origin: Origin, private val pattern: String) :
        RequestDispatcher.IBrokerRequestVoucher<List<ContractDescription>>(
                GenericType.of(List::class.java, ContractDescription::class.java)
                        as GenericType<List<ContractDescription>>, origin) {
    override val interests = setOf(SymbolSamplesEvent::class.java)

    override fun trigger() {
        RequestDispatcher.requestSymbolQuery(this, requestId, pattern)
    }

    override fun notify(info: Information): Boolean =
            if (info is SymbolSamplesEvent) {
                value = info.contractDescrs
                succeed()
                true
            } else super.notify(info)
}
