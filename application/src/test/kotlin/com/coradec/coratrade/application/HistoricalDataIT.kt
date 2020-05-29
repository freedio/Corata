/*
 * Copyright © 2019 by Coradec LLC.  All rights reserved.
 */

package com.coradec.coratrade.application

import com.coradec.coradeck.com.module.CoraCom
import com.coradec.coradeck.core.util.here
import com.coradec.coratrade.interactive.comm.ctrl.impl.HistoricalDataVoucher
import com.coradec.coratrade.interactive.ctrl.RequestDispatcher
import com.coradec.coratrade.interactive.model.DataWindow
import com.coradec.coratrade.interactive.model.HistoryDataSelection
import com.coradec.coratrade.interactive.model.Security
import com.coradec.coratrade.interactive.model.TimeUnit
import com.coradec.coratrade.interactive.model.impl.PrimaryExchange.NYSE
import com.ib.client.Bar
import com.ib.client.Types.BarSize._1_min
import com.ib.client.Types.SecType
import com.ib.client.Types.WhatToShow.BID_ASK
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

class HistoricalDataIT {

    companion object {
        @AfterAll
        @JvmStatic
        fun shutdown() {
            RequestDispatcher.shutdown()
        }
        val log = CoraCom.log
    }

    @Test
    fun testHistoricalData() {
        log.debug("executing testHistoricalData")
        // given
        val security = Security.of(172721087, "UBS", SecType.STK, Currency.getInstance("USD"), primaryXchg = NYSE)
        val selection = HistoryDataSelection.of(LocalDateTime.of(2019, 1, 1, 9, 0, 0).atZone(ZoneId.systemDefault()),
                DataWindow.of(1, TimeUnit.Days), _1_min, BID_ASK, rth = true, formatDate = true)
        val historyRequest = HistoricalDataVoucher(here, security.contract, selection)
        // when
        RequestDispatcher.inject(historyRequest)
        val bars: List<Bar> = historyRequest.value()
        // then
        assertThat(bars).isNotEmpty
    }

}
