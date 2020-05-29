/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model.impl

import com.coradec.coratrade.interactive.model.Exchange
import com.coradec.coratrade.interactive.model.Exchange.SMART
import com.coradec.coratrade.interactive.model.Security
import com.ib.client.Contract
import com.ib.client.Types.Right
import com.ib.client.Types.SecType
import java.util.*

open class BasicSecurity(
        val conId: Int,
        val symbol: String,
        val type: SecType,
        val currency: Currency,
        val exchange: Exchange = SMART,
        val primaryXchg: PrimaryExchange? = null,
        val localSymbol: String? = null,
        val lastTradeDateOrContractMonth: String? = null,
        val strikePrice: Double? = null,
        val right: Right? = null,
        val multiplier: String? = null,
        val tradingClass: String? = null
): Security {
    override val contract: Contract get() {
        val contract = Contract()
        contract.conid(conId)
        contract.symbol(symbol)
        contract.secType(type)
        contract.currency(currency.currencyCode)
        contract.exchange(exchange.name)
        if (primaryXchg != null) contract.primaryExch(primaryXchg.name)
        if (localSymbol != null) contract.localSymbol(localSymbol)
        if (lastTradeDateOrContractMonth != null) contract.lastTradeDateOrContractMonth(lastTradeDateOrContractMonth)
        if (strikePrice != null) contract.strike(strikePrice)
        if (right != null) contract.right(right)
        if (multiplier != null) contract.multiplier(multiplier)
        if (tradingClass != null) contract.tradingClass(tradingClass)
        return contract
    }

}
