package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin
import com.ib.client.DeltaNeutralContract

class DeltaNeutralContractEvent(origin: Origin, requestId: Int, val deltaNeutralContract: DeltaNeutralContract):
    BasicRequestEvent(origin, requestId)
