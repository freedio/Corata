/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model

import com.coradec.coratrade.interactive.model.impl.BasicAccountSummary

interface AccountSummary {
    val accountId: String
    val tag: String
    val value: String
    val currency: String?

    companion object {
        fun of(accountId: String, tag: String, value: String, currency: String?): AccountSummary =
                BasicAccountSummary(accountId, tag, value, currency)
    }

}
