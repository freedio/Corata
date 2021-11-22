package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin
import com.ib.client.SoftDollarTier

class SoftDollarTiersEvent(origin: Origin, requestId: Int, tierList: List<SoftDollarTier>): BasicRequestEvent(origin, requestId)
