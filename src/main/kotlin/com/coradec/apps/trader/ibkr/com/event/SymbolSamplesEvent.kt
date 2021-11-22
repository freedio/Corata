package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin
import com.ib.client.ContractDescription

class SymbolSamplesEvent(origin: Origin, contractDescrList: List<ContractDescription>): InteractiveEvent(origin)
