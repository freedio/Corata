/*
 * Copyright © 2019 by Coradec LLC.  All rights reserved.
 */

package com.coradec.coratrade.application

import com.coradec.coradeck.com.module.CoraCom
import com.coradec.coradeck.core.util.here
import com.coradec.coratrade.interactive.comm.ctrl.impl.SymbolQueryVoucher
import com.coradec.coratrade.interactive.ctrl.RequestDispatcher
import com.ib.client.ContractDescription
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test

class SymbolQueryIT {

    companion object {
        @AfterAll
        @JvmStatic
        fun shutdown() {
            RequestDispatcher.shutdown()
        }

        val log = CoraCom.log
    }

    @Test
    fun testSymbolQuery() {
        log.debug("testSymbolQuery")
        // given
        val symbolQuery = SymbolQueryVoucher(here, "UBS")
        // when
        RequestDispatcher.inject(symbolQuery)
        val symbolList: List<ContractDescription> = symbolQuery.value()
        // then
        assertThat(symbolList).isNotEmpty
        assertThat(symbolList.any { it.contract().symbol() == "UBSG" }).isTrue()
    }

}
