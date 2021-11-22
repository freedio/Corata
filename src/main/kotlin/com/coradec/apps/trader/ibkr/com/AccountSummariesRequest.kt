package com.coradec.apps.trader.ibkr.com

import com.coradec.apps.trader.ibkr.com.event.AccountSummaryPositionEvent
import com.coradec.apps.trader.ibkr.com.event.EndAccountSummaryEvent
import com.coradec.apps.trader.ibkr.com.event.InteractiveEvent
import com.coradec.apps.trader.ibkr.ctrl.RequestId
import com.coradec.apps.trader.ibkr.model.Account
import com.coradec.apps.trader.ibkr.model.AccountField
import com.coradec.apps.trader.ibkr.model.Accounts
import com.coradec.apps.trader.ibkr.trouble.IllegalEventTypeException
import com.coradec.apps.trader.model.AccountValue
import com.coradec.coradeck.core.model.Origin
import com.coradec.coradeck.ctrl.ctrl.IMMEX
import com.coradec.coradeck.ctrl.module.subscribe
import com.coradec.coradeck.ctrl.module.unsubscribe
import com.ib.client.EClientSocket
import java.util.*

class AccountSummariesVoucher(
    origin: Origin, private val group: String, private val tags: EnumSet<AccountField>
) : InteractiveVoucher<Accounts>(origin) {
    private val accounts = mutableMapOf<RequestId, MutableMap<String, MutableMap<String, AccountValue>>>()

    override fun request(socket: EClientSocket) = socket.reqAccountSummary(requestId, group, tags.joinToString(","))
    override fun listen(producer: IMMEX) = subscribe(AccountSummaryPositionEvent::class, EndAccountSummaryEvent::class)
    override fun process(event: InteractiveEvent): Boolean = when (event) {
        is AccountSummaryPositionEvent -> false.also {
            val reqId = event.requestId
            val acct = event.account
            val tag = event.tag
            val value = event.value
            val curr = event.currency
            accounts
                .computeIfAbsent(reqId) { mutableMapOf() }
                .computeIfAbsent(acct) { mutableMapOf() }[tag] =
                AccountValue(value, if (curr != null) Currency.getInstance(curr) else null)
        }
        is EndAccountSummaryEvent -> true.also {
            unsubscribe()
            accounts.remove(event.requestId)?.also { accts ->
                value = Accounts(accts.map { (account, properties) -> Account(account, properties) })
            } ?: emptyList<Account>()
            succeed()
        }
        else -> throw IllegalEventTypeException(event)
    }
}
