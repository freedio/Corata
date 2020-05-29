/*
 * Copyright © 2019 by Coradec LLC.  All rights reserved.
 */

package com.coradec.coratrade.interactive.model

import com.coradec.coradeck.core.trouble.InvalidArgumentException
import com.coradec.coratrade.interactive.model.impl.AllLastTick
import com.coradec.coratrade.interactive.model.impl.SimpleLastTick
import com.ib.client.TickAttribLast
import java.time.ZonedDateTime

interface LastTick : Tick {

    companion object {
        fun of(
                tickType: Int,
                exchange: String,
                systime: ZonedDateTime,
                price: Double,
                size: Int,
                tickAttribLast: TickAttribLast,
                specialConditions: String): LastTick = when (tickType) {
            0 -> SimpleLastTick(exchange, systime, price, size, tickAttribLast, specialConditions)
            1 -> AllLastTick(exchange, systime, price, size, tickAttribLast, specialConditions)
            else -> throw InvalidArgumentException(tickType, "Invalid tick type!")
        }

    }

}
