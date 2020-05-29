/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.model.trouble

import com.coradec.coradeck.com.model.Event

interface ErrorEvent: Event {
    val errorCode: Int
    val errorMessage: String
}
