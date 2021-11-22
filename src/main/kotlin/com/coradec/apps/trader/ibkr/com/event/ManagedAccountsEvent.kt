package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.com.model.impl.BasicEvent
import com.coradec.coradeck.core.model.Origin

class ManagedAccountsEvent(origin: Origin, val accountList: List<String>): BasicEvent(origin)
