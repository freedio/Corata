/*
 * Copyright © 2019 by Coradec LLC.  All rights reserved.
 */

package com.coradec.coratrade.application

import com.coradec.coradeck.com.module.CoraCom
import com.coradec.coradeck.core.util.here
import com.coradec.coratrade.application.model.Switchboard
import com.coradec.coratrade.interactive.comm.ctrl.impl.AccountSummaryVoucher
import com.coradec.coratrade.interactive.comm.model.AccountRecord
import com.coradec.coratrade.interactive.ctrl.RequestDispatcher
import com.coradec.coratrade.interactive.model.AccountField
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class AccountIT {

    companion object {
        @AfterAll
        @JvmStatic
        fun shutdown() {
            RequestDispatcher.shutdown()
        }
        val log = CoraCom.log
    }

    @Test
    fun testManagedAccounts() {
        log.debug("executing testManagedAccounts")
        // given
        // when
        val managedAccounts = Switchboard.managedAccounts
        // then
        assertThat(managedAccounts).isNotEmpty
    }

    @Test
    fun testAccountSummary() {
        log.debug("executing testAccountSummary")
        // given
        val accountSummary = AccountSummaryVoucher(here)
        // when
        RequestDispatcher.inject(accountSummary)
        val accounts: Map<String, AccountRecord> = accountSummary.value()
        // then
        assertThat(accounts).isNotEmpty
        accounts.forEach { (_, accountRecord) ->
            assertThat(accountRecord[AccountField.AccountReady]).isEqualTo("true")
        }
    }

}
