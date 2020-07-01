/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.application.model.contract.impl

import com.coradec.coradeck.data.annot.PrimaryKey
import com.coradec.coradeck.data.annot.Shared
import com.coradec.coradeck.data.annot.Size
import com.coradec.coratrade.application.model.ContractDescriptor
import com.coradec.coratrade.application.model.SecurityType
import com.ib.client.ContractDescription
import java.util.*

open class BasicContractDescriptor(
        override val conID: @PrimaryKey Int,
        override val secID: @Size(64) String,
        override val symbol: @Size(32) String,
        override val localSymbol: @Size(32) String,
        override val type: SecurityType,
        override val primaryExchange: @Shared @Size(16) String,
        override val exchange: @Shared @Size(16) String,
        override val currency: @Size(8) String,
        override val derivativeTypes: List<@Shared @Size(8) String>
): ContractDescriptor {
    constructor(contractDescr: ContractDescription) : this(
            contractDescr.contract().conid(),
            contractDescr.contract().secId() ?: "",
            contractDescr.contract().symbol(),
            contractDescr.contract().localSymbol() ?: "",
            SecurityType.valueOf(contractDescr.contract().secType),
            contractDescr.contract().primaryExch(),
            contractDescr.contract().exchange() ?: "",
            contractDescr.contract().currency(),
            contractDescr.derivativeSecTypes().toList()
    )
}
