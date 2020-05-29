/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.model

import com.coradec.coratrade.interactive.model.AccountField
import com.coradec.coratrade.interactive.model.AccountSummary


interface AccountRecord {
    /** The record as a map of account properties. */
    val entries: Map<AccountField, Any>

    /** Adds the specified account summary to the record. */
    operator fun plusAssign(data: AccountSummary)
    /** Returns the value of the specified account propetry. */
    operator fun get(field: AccountField): Any?
    /** Updates the account from the specified record. */
    fun updateFrom(from: AccountRecord)
}
