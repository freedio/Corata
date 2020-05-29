/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.application.ctrl.impl

import com.coradec.coradeck.bus.meta.reachedState
import com.coradec.coradeck.bus.model.BusNodeState.INITIALIZED
import com.coradec.coradeck.bus.model.impl.BasicBusModule
import com.coradec.coratrade.application.app.Application

class MarketDataModule: BasicBusModule() {
    val persistence by reachedState(INITIALIZED) { Application.persistence }

    override fun onStarted() {
        super.onStarted()
        persistence
    }
}
