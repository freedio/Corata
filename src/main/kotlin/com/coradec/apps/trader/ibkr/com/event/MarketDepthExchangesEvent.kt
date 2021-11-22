package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin
import com.ib.client.DepthMktDataDescription

class MarketDepthExchangesEvent(origin: Origin, val descrList: List<DepthMktDataDescription>): InteractiveEvent(origin)
