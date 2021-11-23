package com.coradec.apps.trader.ibkr.trouble

import com.coradec.coradeck.core.trouble.BasicException

open class InteractiveException(message: String?, problem: Throwable?) : BasicException(message, problem) {
    constructor(message: String) : this(message, null)
    constructor() : this(null, null)
}
