/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model

import com.coradec.coratrade.interactive.model.impl.BasicSizeTick

interface SizeTick : Tick {

    companion object {
        fun of(field: Int, size: Int): SizeTick = BasicSizeTick(field, size)
    }

}
