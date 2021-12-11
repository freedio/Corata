package com.coradec.apps.trader.ibkr.model

data class Account(val name: String, val properties: MutableMap<AccountField, Any>)
