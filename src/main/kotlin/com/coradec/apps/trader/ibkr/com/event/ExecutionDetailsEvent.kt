package com.coradec.apps.trader.ibkr.com.event

import com.coradec.apps.trader.ibkr.com.event.BasicContractEvent
import com.coradec.coradeck.core.model.Origin
import com.ib.client.Contract
import com.ib.client.Execution

class ExecutionDetailsEvent(origin: Origin, requestId: Int, contract: Contract, val execution: Execution):
    BasicContractEvent(origin, contract)
