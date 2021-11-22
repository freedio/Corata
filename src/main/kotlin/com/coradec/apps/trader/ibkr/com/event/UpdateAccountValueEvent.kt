package com.coradec.apps.trader.ibkr.com.event

import com.coradec.apps.trader.ibkr.com.event.BasicAccountEvent
import com.coradec.coradeck.core.model.Origin

class UpdateAccountValueEvent(origin: Origin, accountName: String, val key: String, val value: String, val currency: String?):
        BasicAccountEvent(origin, accountName)
