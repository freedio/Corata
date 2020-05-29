/*
 * Copyright © 2019 by Coradec LLC.  All rights reserved.
 */

package com.coradec.coratrade.interactive.model.impl

import com.ib.client.TickAttribLast
import java.time.ZonedDateTime

class AllLastTick(
        exchange: String,
        timestamp: ZonedDateTime,
        price: Double,
        size: Int,
        tickAttribLast: TickAttribLast,
        specialConditions: String) : BasicLastTick(exchange, timestamp, price, size, tickAttribLast, specialConditions)
