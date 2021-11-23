package com.coradec.apps.trader.com

import com.coradec.apps.trader.ibkr.model.Accounts
import com.coradec.coradeck.com.model.impl.BasicEvent
import com.coradec.coradeck.core.model.Origin

class AccountsDiscoveredEvent(origin: Origin, val value: Accounts): BasicEvent(origin)
