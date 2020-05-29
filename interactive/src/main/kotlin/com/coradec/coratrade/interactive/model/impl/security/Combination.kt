/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model.impl.security

import com.coradec.coratrade.interactive.model.impl.BasicSecurity
import com.coradec.coratrade.interactive.model.impl.PrimaryExchange
import com.ib.client.Types
import java.util.*

class Combination(contractId: Int, symbol: String, currency: Currency, primaryXchg: PrimaryExchange) :
        BasicSecurity(contractId, symbol, Types.SecType.BAG, currency, primaryXchg = primaryXchg) {

}
