/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model.impl

import com.coradec.coratrade.interactive.model.PriceTick
import com.ib.client.TickAttrib

class BasicPricetick(
        val field: Int,
        val price: Double,
        val attributes: TickAttrib
) : PriceTick
