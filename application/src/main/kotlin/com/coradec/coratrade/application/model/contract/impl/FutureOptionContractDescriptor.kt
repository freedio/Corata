/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.application.model.contract.impl

import com.ib.client.ContractDescription

class FutureOptionContractDescriptor(descr: ContractDescription) : BasicContractDescriptor(descr) {
    val multiplier = descr.contract().multiplier()
}
