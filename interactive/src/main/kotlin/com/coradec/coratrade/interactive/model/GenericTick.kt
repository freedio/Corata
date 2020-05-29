/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model

import com.coradec.coratrade.interactive.model.impl.BasicGenericTick

interface GenericTick: Tick {

    companion object {
        fun of(field: Int, value: Double): GenericTick = BasicGenericTick(field, value)
    }

}
