package com.coradec.apps.trader.trouble

import com.coradec.coradeck.core.trouble.BasicException

class AccountNotFoundException(val accountName: String) : BasicException()
