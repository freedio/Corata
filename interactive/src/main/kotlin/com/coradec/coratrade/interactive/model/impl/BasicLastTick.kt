/*
 * Copyright © 2019 by Coradec LLC.  All rights reserved.
 */

package com.coradec.coratrade.interactive.model.impl

import com.coradec.coratrade.interactive.model.LastTick
import com.ib.client.TickAttribLast
import java.time.ZonedDateTime

open class BasicLastTick(
        val exchange: String,
        val timestamp: ZonedDateTime,
        val price: Double,
        val size: Int,
        val tickAttribLast: TickAttribLast,
        val specialConditions: String
): LastTick
