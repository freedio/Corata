/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model

import com.coradec.coratrade.interactive.model.impl.PrimaryExchange
import com.coradec.coratrade.interactive.model.impl.security.*
import com.ib.client.Contract
import com.ib.client.Types.SecType
import com.ib.client.Types.SecType.*
import java.util.*

interface Security {
    val contract: Contract

    companion object {
        fun of(
                contractId: Int,
                symbol: String,
                type: SecType,
                currency: Currency,
                primaryXchg: PrimaryExchange
        ): Security =
                when (type) {
                    None -> NoSecurity(contractId, symbol, currency, primaryXchg)
                    STK -> Stock(contractId, symbol, currency, primaryXchg)
                    OPT -> Option(contractId, symbol, currency, primaryXchg)
                    FUT -> Future(contractId, symbol, currency, primaryXchg)
                    CONTFUT -> ContinuousFuture(contractId, symbol, currency, primaryXchg)
                    CASH -> CashSecurity(contractId, symbol, currency, primaryXchg)
                    BOND -> Bond(contractId, symbol, currency, primaryXchg)
                    CFD -> ContractForDifference(contractId, symbol, currency, primaryXchg)
                    FOP -> FreeOfPaymentSecurity(contractId, symbol, currency, primaryXchg)
                    WAR -> Warrant(contractId, symbol, currency, primaryXchg)
                    IOPT -> StructuredOption(contractId, symbol, currency, primaryXchg)
                    FWD -> Forward(contractId, symbol, currency, primaryXchg)
                    BAG -> Combination(contractId, symbol, currency, primaryXchg)
                    IND -> IND_(contractId, symbol, currency, primaryXchg)
                    BILL -> TreasuryBill(contractId, symbol, currency, primaryXchg)
                    FUND -> Fund(contractId, symbol, currency, primaryXchg)
                    FIXED -> FIXED_(contractId, symbol, currency, primaryXchg)
                    SLB -> StockLoanBorrow(contractId, symbol, currency, primaryXchg)
                    NEWS -> NEWS_(contractId, symbol, currency, primaryXchg)
                    CMDTY -> Commodity(contractId, symbol, currency, primaryXchg)
                    BSK -> Basket(contractId, symbol, currency, primaryXchg)
                    ICU -> ICU_(contractId, symbol, currency, primaryXchg)
                    ICS -> ICS_(contractId, symbol, currency, primaryXchg)
                }

    }
}
