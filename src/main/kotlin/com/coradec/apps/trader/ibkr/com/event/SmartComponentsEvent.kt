package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin

class SmartComponentsEvent(
    origin: Origin, requestId: Int, val smartComponents: Map<Int, Map.Entry<String, Char>>
): BasicRequestEvent(origin, requestId)
