/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model

import com.coradec.coratrade.interactive.model.impl.BasicNewBulletin

interface NewsBulletin {
    companion object {
        fun of(messageId: Int, messageType: Int, message: String, origExch: String): NewsBulletin =
                BasicNewBulletin(messageId, messageType, message, origExch)
    }
}
