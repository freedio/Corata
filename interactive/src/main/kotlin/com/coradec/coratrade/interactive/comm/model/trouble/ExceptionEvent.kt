/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.model.trouble

interface ExceptionEvent: ErrorEvent {
    val exception: Exception
}
