/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model.impl

import com.coradec.coratrade.interactive.model.RequestParameterTick

class BasicRequestParameterTick(
        val minTick: Double,
        val bboExchange: String,
        val snapshotPermissions: Int
) : RequestParameterTick {

}
