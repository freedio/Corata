/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.trouble

import com.coradec.coradeck.core.trouble.BasicException

open class CoraTradeException(message: String?, problem: Throwable?) : BasicException(message, problem) {
    constructor(message: String) : this(message, null)
    constructor() : this(null, null)
}
