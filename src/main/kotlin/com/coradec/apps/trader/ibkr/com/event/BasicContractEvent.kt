package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin
import com.ib.client.Contract

open class BasicContractEvent(origin: Origin, val contract: Contract): InteractiveEvent(origin)
