package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin

class AccountUpdatedEvent(origin: Origin, accountName: String): BasicAccountEvent(origin, accountName)
