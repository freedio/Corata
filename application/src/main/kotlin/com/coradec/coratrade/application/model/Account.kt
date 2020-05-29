/*
 * Copyright © 2019 by Coradec LLC.  All rights reserved.
 */

package com.coradec.coratrade.application.model

import com.coradec.coratrade.interactive.comm.model.AccountRecord

interface Account {
    /** Indicates whether the account has been deleted (`false`) or not. */
    var active: Boolean

    /** Updates the account attributes from the specified record. */
    fun updateAccountAttributes(from: AccountRecord)
}
