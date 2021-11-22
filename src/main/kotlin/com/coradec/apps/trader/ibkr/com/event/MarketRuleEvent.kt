package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin
import com.ib.client.PriceIncrement

class MarketRuleEvent(origin: Origin, val ruleId: Int, val incs: List<PriceIncrement>): InteractiveEvent(origin)
