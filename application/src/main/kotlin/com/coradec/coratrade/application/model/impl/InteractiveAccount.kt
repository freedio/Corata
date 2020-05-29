/*
 * Copyright © 2019 by Coradec LLC.  All rights reserved.
 */

package com.coradec.coratrade.application.model.impl

import com.coradec.coradeck.data.annot.PrimaryKey
import com.coradec.coradeck.data.annot.Size
import com.coradec.coratrade.application.model.Account
import com.coradec.coratrade.interactive.comm.model.AccountRecord

data class InteractiveAccount(val name: @PrimaryKey @Size(64) String, val record: AccountRecord) : Account {
    override var active: Boolean = true

    override fun updateAccountAttributes(from: AccountRecord) {
        record.updateFrom(from)
    }
}
