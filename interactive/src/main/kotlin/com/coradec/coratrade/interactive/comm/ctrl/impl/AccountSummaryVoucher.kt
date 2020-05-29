/*
 * Copyright © 2019 by Coradec LLC.  All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.ctrl.impl

import com.coradec.coradeck.com.model.Information
import com.coradec.coradeck.core.ctrl.Origin
import com.coradec.coradeck.type.model.GenericType
import com.coradec.coratrade.interactive.comm.model.AccountRecord
import com.coradec.coratrade.interactive.comm.model.impl.AccountSummaryEvent
import com.coradec.coratrade.interactive.comm.model.impl.BasicAccountRecord
import com.coradec.coratrade.interactive.comm.model.impl.EndAccoutSummaryEvent
import com.coradec.coratrade.interactive.ctrl.RequestDispatcher
import com.coradec.coratrade.interactive.model.AccountField
import java.util.*

@Suppress("UNCHECKED_CAST")
class AccountSummaryVoucher(
        origin: Origin,
        private val account: String = "All",
        private val fields: EnumSet<AccountField> = EnumSet.allOf(AccountField::class.java)
) : RequestDispatcher.IBrokerVoucher<Map<String, AccountRecord>>(
                GenericType.of(Map::class.java, String::class.java, AccountRecord::class.java)
                        as GenericType<Map<String, AccountRecord>>, origin) {
    override val interests = setOf(AccountSummaryEvent::class.java, EndAccoutSummaryEvent::class.java)
    private val requestId: Int = RequestDispatcher.nextRequestId
    private val accountRecords = mutableMapOf<String, AccountRecord>()

    override fun trigger() {
        RequestDispatcher.requestAccountSummaries(this, requestId, account, fields)
    }

    override fun notify(info: Information): Boolean =
            if (info is AccountSummaryEvent && info.requestId == requestId) {
                val name = info.acctSummary.accountId
                accountRecords.computeIfAbsent(name) { BasicAccountRecord(name) } += info.acctSummary
                false
            } else if (info is EndAccoutSummaryEvent && info.requestId == requestId) {
                value = accountRecords
                succeed()
                true
            } else super.notify(info)
}
