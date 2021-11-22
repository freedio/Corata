package com.coradec.apps.trader.ibkr.com.event

import com.coradec.apps.trader.ibkr.com.event.BasicContractDetailsEvent
import com.coradec.coradeck.core.model.Origin

class EndContractDetailsEvent(origin: Origin, requestId: Int): BasicContractDetailsEvent(origin, requestId)
