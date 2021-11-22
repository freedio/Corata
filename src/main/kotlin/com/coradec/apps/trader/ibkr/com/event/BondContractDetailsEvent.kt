package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin
import com.ib.client.ContractDetails

class BondContractDetailsEvent(origin: Origin, requestId: Int, val contractDetails: ContractDetails):
    BasicContractDetailsEvent(origin, requestId)
