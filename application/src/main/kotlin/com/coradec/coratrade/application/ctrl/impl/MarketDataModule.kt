/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.application.ctrl.impl

import com.coradec.coradeck.bus.meta.reachedState
import com.coradec.coradeck.bus.model.BusNodeState.ATTACHED
import com.coradec.coradeck.bus.model.BusNodeState.INITIALIZED
import com.coradec.coradeck.bus.model.BusNodeState.INITIALIZING
import com.coradec.coradeck.bus.model.impl.BasicBusModule
import com.coradec.coradeck.ctrl.model.DynamicList
import com.coradec.coradeck.ctrl.model.impl.BasicDynamicList
import com.coradec.coradeck.data.ctrl.RecordSelector
import com.coradec.coradeck.data.ctrl.RecordSelector.Companion
import com.coradec.coradeck.data.view.PersistenceView
import com.coradec.coradeck.dir.module.CoraDir
import com.coradec.coradeck.session.module.CoraSession
import com.coradec.coratrade.application.app.Application
import com.coradec.coratrade.interactive.model.Security

class MarketDataModule: BasicBusModule() {
    private val persistence by reachedState(ATTACHED) { Application.persistence }

    override fun onReady() {
        super.onReady()
        detail("Loading securities...")
        val securities = persistence.find(RecordSelector.matchingAll(Security::class))
        detail("Loaded %d securities.", securities.size)
        finish()
    }

}
