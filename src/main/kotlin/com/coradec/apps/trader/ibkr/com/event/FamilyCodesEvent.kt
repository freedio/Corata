package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin
import com.ib.client.FamilyCode

class FamilyCodesEvent(origin: Origin, codeList: List<FamilyCode>): InteractiveEvent(origin)
