package com.coradec.apps.trader.ibkr.com.event

import com.coradec.apps.trader.ibkr.com.event.BasicAccountEvent
import com.coradec.coradeck.core.model.Origin
import com.ib.client.Contract

class UpdatePortfolioEvent(
    origin: Origin, accountName: String, val contract: Contract, val position: Long, val marketPrice: Double,
    val marketValue: Double, val averageCost: Double, val unrealizedPNL: Double, val realizedPNL: Double
) : BasicAccountEvent(origin, accountName)
