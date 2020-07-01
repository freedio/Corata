/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.application.model

import com.coradec.coradeck.data.annot.Shared
import com.coradec.coradeck.data.annot.Size
import com.coradec.coratrade.application.model.contract.impl.BasicContractDescriptor
import com.ib.client.ContractDescription
import java.util.*

interface ContractDescriptor {
    val conID: Int
    val secID: @Size(16) String
    val symbol: @Size(32) String
    val localSymbol: @Size(32) String
    val type: SecurityType
    val primaryExchange: @Shared @Size(16) String
    val exchange: @Shared @Size(16) String
    val currency: @Shared @Size(8) String
    val derivativeTypes: List<@Shared @Size(8) String>

    companion object {
        fun of(descr: ContractDescription): ContractDescriptor =
                SecurityType.valueOf(descr.contract().secType).createContractDescriptor(descr)
    }
}
