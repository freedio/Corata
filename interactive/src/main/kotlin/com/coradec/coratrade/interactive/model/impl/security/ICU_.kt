/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model.impl.security

import com.coradec.coratrade.interactive.model.impl.BasicSecurity
import com.coradec.coratrade.interactive.model.impl.PrimaryExchange
import com.ib.client.Types
import java.util.*

class ICU_(contractId: Int, symbol: String, currency: Currency, primaryXchg: PrimaryExchange) :
        BasicSecurity(contractId, symbol, Types.SecType.ICU, currency,primaryXchg = primaryXchg)
