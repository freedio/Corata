package com.coradec.apps.trader.ibkr.com.event

import com.coradec.apps.trader.ibkr.model.FinancialAdvisorDataType
import com.coradec.coradeck.com.model.impl.BasicEvent
import com.coradec.coradeck.core.model.Origin

class FinancialAdvisorConfigurationEvent(origin: Origin, val type: FinancialAdvisorDataType, val faXmlData: String):
        BasicEvent(origin)
