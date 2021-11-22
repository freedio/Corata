package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin

class AccountUpdateTimeEvent(origin: Origin, accountName: String, val timestamp: String): BasicAccountEvent(origin, accountName)
