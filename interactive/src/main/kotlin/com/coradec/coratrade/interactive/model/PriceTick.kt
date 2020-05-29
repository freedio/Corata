/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model

import com.coradec.coratrade.interactive.model.impl.BasicPricetick
import com.ib.client.TickAttrib

interface PriceTick : Tick {

    companion object {
        fun of(field: Int, price: Double, attributes: TickAttrib): PriceTick = BasicPricetick(field, price, attributes)

    }

}
