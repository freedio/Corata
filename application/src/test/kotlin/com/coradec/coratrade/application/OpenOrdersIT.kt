/*
 * Copyright © 2019 by Coradec LLC.  All rights reserved.
 */

package com.coradec.coratrade.application

import com.coradec.coradeck.com.module.CoraCom
import com.coradec.coradeck.core.util.here
import com.coradec.coratrade.interactive.comm.ctrl.impl.OpenOrdersVoucher
import com.coradec.coratrade.interactive.ctrl.RequestDispatcher
import com.coradec.coratrade.interactive.model.ContractedOrder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test

class OpenOrdersIT {

    companion object {
        @AfterAll
        @JvmStatic
        fun shutdown() {
            RequestDispatcher.shutdown()
        }
        val log = CoraCom.log
    }

    @Test
    fun testOpenOrders() {
        log.debug("executing testOpenOrders")
        // given
        val openOrders = OpenOrdersVoucher(here)
        // when
        RequestDispatcher.inject(openOrders)
        val orders: Map<Int, ContractedOrder> = openOrders.value()
        // then
        assertThat(orders).isNotNull
    }

}
