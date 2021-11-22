package com.coradec.apps.trader.ibkr.com.event

import com.coradec.apps.trader.ibkr.com.event.BasicScannerEvent
import com.coradec.coradeck.core.model.Origin
import com.ib.client.ContractDetails

class ScannerDataEvent(
    origin: Origin, requestId: Int, val rank: Int, val contractDetails: ContractDetails, val distance: String,
    val benchmark: String, val projection: String, val comboLegs: String
): BasicScannerEvent(origin, requestId)
