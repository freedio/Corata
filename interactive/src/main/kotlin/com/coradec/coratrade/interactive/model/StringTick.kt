/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model

import com.coradec.coratrade.interactive.model.impl.BasicStringTick

interface StringTick : Tick {

    companion object {
        fun of(field: Int, value: String): StringTick = BasicStringTick(field, value)
    }

}
