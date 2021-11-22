package com.coradec.apps.trader.ctrl

import com.coradec.apps.trader.com.impl.HandleTitleRequest
import com.coradec.apps.trader.model.Title
import com.coradec.apps.trader.model.impl.BasicTitle
import com.coradec.coradeck.bus.model.impl.BasicBusHub
import com.coradec.coradeck.bus.view.BusContext
import com.coradec.coradeck.ctrl.module.subscribe
import com.coradec.coradeck.ctrl.module.unsubscribe
import com.coradec.coradeck.db.model.Database
import com.coradec.coradeck.db.model.RecordTable

class TitleProcessor(val db: Database) : BasicBusHub() {
    private val titles: RecordTable<Title> by lazy { db.openTable(BasicTitle::class) }

    override fun onAttached(context: BusContext) {
        route(HandleTitleRequest::class, ::handleTitle)
        subscribe(HandleTitleRequest::class)
        super.onAttached(context)
    }

    override fun onFinalized() {
        unsubscribe()
        unroute(HandleTitleRequest::class)
        super.onFinalized()
    }

    private fun handleTitle(request: HandleTitleRequest) {
        try {
            val title = request.title
            debug("TitleProcessor: Handling title «%s».", title)
            request.succeed()
        } catch (e: Exception) {
            request.fail(e)
        }
    }
}
