package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin
import com.coradec.coradeck.core.model.Priority.B3

class EndContractDetailsEvent(origin: Origin, requestId: Int): BasicContractDetailsEvent(origin, requestId, B3)
