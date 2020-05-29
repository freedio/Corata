/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.application.model

import com.coradec.coradeck.com.module.CoraCom
import com.coradec.coradeck.core.util.pretty
import com.coradec.coratrade.interactive.ctrl.RequestDispatcher
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test

internal class SwitchboardTest {

    companion object {
        //        @BeforeAll
//        @JvmStatic
//        fun setup() {
//            Application.startup()
//        }
        @AfterAll
        @JvmStatic
        fun shutdown() {
            Thread.sleep(1000)
            RequestDispatcher.shutdown()
        }
        val log = CoraCom.log
    }

    val testee = Switchboard

    @Test
    fun testServerTimeLag() {
        log.debug("executing testServerTimeLag")
        val serverTimeLag = Switchboard.serverTimeLag
        log.debug("Server time lag: %s", serverTimeLag.pretty)
        assertThat(serverTimeLag).isEqualTo(RequestDispatcher.serverTimeLag.Δt)
    }

    @Test
    fun testManagedAccounts() {
        log.debug("executing testManagedAccounts")
        val managedAccounts = Switchboard.managedAccounts
        log.debug("Managed accounts: %s", managedAccounts)
        assertThat(managedAccounts).isNotEmpty
    }

    @Test
    fun testOrderId() {
        log.debug("executing testOrderId")
        val orderId1 = Switchboard.orderId
        log.debug("Order 1 ID = %d", orderId1)
        assertThat(orderId1).isGreaterThan(0)
        val orderId2 = Switchboard.orderId
        log.debug("Order 2 ID = %d", orderId2)
        assertThat(orderId2).isEqualTo(orderId1 + 1)
    }

}
