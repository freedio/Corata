/*
 * Copyright © 2019 by Coradec LLC.  All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.ctrl.impl

import com.coradec.coradeck.com.model.Information
import com.coradec.coradeck.core.ctrl.Origin
import com.coradec.coradeck.type.model.GenericType
import com.coradec.coratrade.interactive.comm.model.impl.ManagedAccountsEvent
import com.coradec.coratrade.interactive.ctrl.RequestDispatcher

@Suppress("UNCHECKED_CAST")
class ManagedAccountsVoucher(origin: Origin) :
        RequestDispatcher.IBrokerVoucher<Set<String>>(
                GenericType.of(Set::class.java, String::class.java) as GenericType<Set<String>>, origin) {
    override val interests = setOf(ManagedAccountsEvent::class.java)

    override fun trigger() {
        RequestDispatcher.requestManagedAccounts(this)
    }

    override fun notify(info: Information): Boolean =
            if (info is ManagedAccountsEvent) {
                value = info.accountList
                succeed()
                true
            } else super.notify(info)
}
