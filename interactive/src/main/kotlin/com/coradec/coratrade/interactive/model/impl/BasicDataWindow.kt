/*
 * Copyright © 2019 by Coradec LLC.  All rights reserved.
 */

package com.coradec.coratrade.interactive.model.impl

import com.coradec.coratrade.interactive.model.DataWindow
import com.coradec.coratrade.interactive.model.TimeUnit

class BasicDataWindow(override val size: Int, override val unit: TimeUnit) : DataWindow {
    override fun equals(other: Any?): Boolean = other is DataWindow && size == other.size && unit == other.unit
    override fun hashCode(): Int = size * 3 + unit.ordinal * 7
    override fun toString(): String = "$size ${unit.repr}"
}
