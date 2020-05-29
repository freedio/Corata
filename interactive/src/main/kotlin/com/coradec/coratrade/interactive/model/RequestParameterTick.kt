/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model

import com.coradec.coratrade.interactive.model.impl.BasicRequestParameterTick

interface RequestParameterTick : Tick {

    companion object {
        fun of(minTick: Double, bboExchange: String, snapshotPermissions: Int): RequestParameterTick =
                BasicRequestParameterTick(minTick, bboExchange, snapshotPermissions)
    }

}
