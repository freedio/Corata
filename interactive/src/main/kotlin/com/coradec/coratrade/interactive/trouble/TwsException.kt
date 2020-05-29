/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.trouble

open class TwsException(val errorCode: Int, errorMessage: String) : CoraTradeException(errorMessage)
