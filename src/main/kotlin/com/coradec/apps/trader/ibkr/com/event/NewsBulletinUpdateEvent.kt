package com.coradec.apps.trader.ibkr.com.event

import com.coradec.apps.trader.ibkr.com.event.BasicMessageEvent
import com.coradec.apps.trader.ibkr.model.NewsBulletinState
import com.coradec.coradeck.core.model.Origin

class NewsBulletinUpdateEvent(origin: Origin, msgId: Int, val type: NewsBulletinState, val message: String, val origXchg: String):
    BasicMessageEvent(origin, msgId)
