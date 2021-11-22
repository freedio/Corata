package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin

class DisplayGroupsEvent(origin: Origin, requestId: Int, groups: String): BasicRequestEvent(origin, requestId)
