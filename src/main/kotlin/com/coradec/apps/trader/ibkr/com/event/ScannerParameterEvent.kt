package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin

class ScannerParameterEvent(origin: Origin, parameters: String): InteractiveEvent(origin)
