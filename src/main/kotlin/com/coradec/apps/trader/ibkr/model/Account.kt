package com.coradec.apps.trader.ibkr.model

import com.coradec.apps.trader.model.AccountValue

data class Account(val name: String, val properties: Map<String, AccountValue>)
