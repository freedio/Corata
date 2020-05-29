/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.trouble

class RequestFailure(errorCode: Int, errorMessage: String) : TwsException(errorCode, errorMessage)
