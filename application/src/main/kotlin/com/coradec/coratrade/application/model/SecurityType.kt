/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.application.model

import com.coradec.coradeck.data.annot.Size
import com.coradec.coratrade.application.model.contract.impl.BondContractDescriptor
import com.coradec.coratrade.application.model.contract.impl.CashContractDescriptor
import com.coradec.coratrade.application.model.contract.impl.ComboContractDescriptor
import com.coradec.coratrade.application.model.contract.impl.CommodityContractDescriptor
import com.coradec.coratrade.application.model.contract.impl.FundContractDescriptor
import com.coradec.coratrade.application.model.contract.impl.FutureContractDescriptor
import com.coradec.coratrade.application.model.contract.impl.FutureOptionContractDescriptor
import com.coradec.coratrade.application.model.contract.impl.IndexContractDescriptor
import com.coradec.coratrade.application.model.contract.impl.NewsContractDescriptor
import com.coradec.coratrade.application.model.contract.impl.OptionContractDescriptor
import com.coradec.coratrade.application.model.contract.impl.StockContractDescriptor
import com.coradec.coratrade.application.model.contract.impl.WarrantContractDescriptor
import com.ib.client.ContractDescription

enum class SecurityType(val title: @Size(32) String) {
    STK("Stock / ETF") {
        override fun createContractDescriptor(descr: ContractDescription): ContractDescriptor = StockContractDescriptor(descr)
    },
    OPT("Option") {
        override fun createContractDescriptor(descr: ContractDescription): ContractDescriptor = OptionContractDescriptor(descr)
    },
    FUT("Future") {
        override fun createContractDescriptor(descr: ContractDescription): ContractDescriptor = FutureContractDescriptor(descr)
    },
    IND("Index") {
        override fun createContractDescriptor(descr: ContractDescription): ContractDescriptor = IndexContractDescriptor(descr)
    },
    FOP("Futures Option") {
        override fun createContractDescriptor(descr: ContractDescription): ContractDescriptor =
                FutureOptionContractDescriptor(descr)
    },
    CASH("Forex Pair") {
        override fun createContractDescriptor(descr: ContractDescription): ContractDescriptor = CashContractDescriptor(descr)
    },
    BAG("Combo") {
        override fun createContractDescriptor(descr: ContractDescription): ContractDescriptor = ComboContractDescriptor(descr)
    },
    WAR("Warrant") {
        override fun createContractDescriptor(descr: ContractDescription): ContractDescriptor = WarrantContractDescriptor(descr)
    },
    BOND("Bond") {
        override fun createContractDescriptor(descr: ContractDescription): ContractDescriptor = BondContractDescriptor(descr)
    },
    CMDTY("Commodity") {
        override fun createContractDescriptor(descr: ContractDescription): ContractDescriptor =
                CommodityContractDescriptor(descr)
    },
    NEWS("News") {
        override fun createContractDescriptor(descr: ContractDescription): ContractDescriptor = NewsContractDescriptor(descr)
    },
    FUND("Mutual fund") {
        override fun createContractDescriptor(descr: ContractDescription): ContractDescriptor = FundContractDescriptor(descr)
    };

    abstract fun createContractDescriptor(descr: ContractDescription): ContractDescriptor
}
