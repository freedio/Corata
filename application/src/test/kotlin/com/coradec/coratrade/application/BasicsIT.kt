/*
 * Copyright © 2019 by Coradec LLC.  All rights reserved.
 */

package com.coradec.coratrade.application

import com.coradec.coradeck.com.module.CoraCom
import com.coradec.coratrade.application.model.Switchboard
import com.coradec.coratrade.interactive.ctrl.RequestDispatcher
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test

class BasicsIT {

    companion object {
        @AfterAll
        @JvmStatic
        fun shutdown() {
            Thread.sleep(1000)
            RequestDispatcher.shutdown()
        }
        val log = CoraCom.log
    }

    @Test
    fun testConnection() {
        log.debug("executing testConnection")
        // given
        // when
        val connected = RequestDispatcher.connected
        // then
        assertThat(connected).isTrue()
    }

    @Test
    fun testManagedAccounts() {
        log.debug("executing testManagedAccounts")
        // given
        // when
        val accounts = Switchboard.managedAccounts
        // then
        assertThat(accounts).isNotEmpty
    }

    @Test
    fun testOrderID() {
        log.debug("executing testOrderId")
        // given
        // when
        val nextOrderId = Switchboard.orderId
        // then
        assertThat(nextOrderId).isGreaterThan(0)
    }

    @Test
    fun testServerTimeLag() {
        log.debug("executing testServerTimeLag")
        // given
        // when
        val serverTimeLag = Switchboard.serverTimeLag
        // then
        assertThat(serverTimeLag).isNotNull()
    }

}
