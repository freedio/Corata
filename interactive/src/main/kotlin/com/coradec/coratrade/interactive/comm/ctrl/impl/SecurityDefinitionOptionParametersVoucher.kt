/*
 * Copyright © 2019 by Coradec LLC.  All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.ctrl.impl

import com.coradec.coradeck.com.model.Information
import com.coradec.coradeck.core.ctrl.Origin
import com.coradec.coradeck.type.model.GenericType
import com.coradec.coratrade.interactive.comm.model.impl.ContractDetailsEvent
import com.coradec.coratrade.interactive.comm.model.impl.EndContractDetailsEvent
import com.coradec.coratrade.interactive.ctrl.RequestDispatcher
import com.ib.client.Contract
import com.ib.client.ContractDetails

@Suppress("UNCHECKED_CAST")
class SecurityDefinitionOptionParametersVoucher(origin: Origin, private val contract: Contract) :
        RequestDispatcher.IBrokerRequestVoucher<List<ContractDetails>>(
                GenericType.of(List::class.java, ContractDetails::class.java) as GenericType<List<ContractDetails>>, origin) {
    override val interests =
            setOf(ContractDetailsEvent::class.java, EndContractDetailsEvent::class.java)
    private val contractDetails: MutableList<ContractDetails> = mutableListOf()

    override fun trigger() {
        RequestDispatcher.requestContractDetails(this, requestId, contract)
    }

    override fun notify(info: Information): Boolean = when (info) {
        is EndContractDetailsEvent -> {
            value = contractDetails
            succeed()
            true
        }
        is ContractDetailsEvent -> {
            contractDetails += info.contractDetails
            false
        }
        else -> super.notify(info)
    }
}
