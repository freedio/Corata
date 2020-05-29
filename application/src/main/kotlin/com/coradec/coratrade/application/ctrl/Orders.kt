/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.application.ctrl

import com.coradec.coradeck.data.module.CoraData
import com.coradec.coratrade.application.model.impl.InteractiveOrder

/** The order controller façade. */
object Orders {
    val orders: MutableList<InteractiveOrder> = mutableListOf()

    fun preload() {
        // dummy function to start preloading the orders
    }
}
