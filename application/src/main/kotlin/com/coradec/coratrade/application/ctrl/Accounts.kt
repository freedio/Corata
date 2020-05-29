/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.application.ctrl

import com.coradec.coradeck.com.model.Recipient
import com.coradec.coradeck.core.ctrl.Origin
import com.coradec.coradeck.ctrl.com.impl.BasicCommand
import com.coradec.coradeck.ctrl.ctrl.impl.BasicAgent
import com.coradec.coradeck.data.module.CoraData
import com.coradec.coradeck.session.module.CoraSession
import com.coradec.coradeck.text.model.LocalizedText
import com.coradec.coratrade.application.model.Switchboard.managedAccounts
import com.coradec.coratrade.application.model.impl.InteractiveAccount
import com.coradec.coratrade.interactive.comm.ctrl.impl.AccountSummaryVoucher
import com.coradec.coratrade.interactive.comm.model.AccountRecord

/** The account controller façade. */
object Accounts : BasicAgent() {
    private val TEXT_ACCOUNT_DELETED = LocalizedText.define("AccountDeleted")
    private val TEXT_ACCOUNT_CREATED = LocalizedText.define("AccountCreated")
    private val accounts: MutableList<InteractiveAccount> = mutableListOf()
    private val persistence = CoraData.getPersistence(CoraSession.new)

    init {
        approve(ProcessSummaryCommand::class.java)
    }

    fun preload() {
        val accts = managedAccounts
        accounts.forEach { account ->
            with (account.name) { if (this !in accts) deleteAccount(this) }
        }
//        accts.forEach { account ->
//            if (account !in accounts) createAccount(account, Account(...))
//        }
        updateAccountSummaries()
    }

    private fun updateAccountSummaries() {
        val summaries = AccountSummaryVoucher(this)
        inject(summaries andThen ProcessSummaryCommand(this, this, summaries))
    }

    private fun createAccount(name: String, record: AccountRecord) {
        accounts += InteractiveAccount(name, record).also { persistence.add(it) }
        info(TEXT_ACCOUNT_CREATED, name)
    }

    private fun deleteAccount(name: String) {
        accounts.filter { it.name == name }.forEach { it.active = false }
        alert(TEXT_ACCOUNT_DELETED, name)
    }

    private fun updateAccount(account: InteractiveAccount, record: AccountRecord) {
        account.updateAccountAttributes(record)
    }

    class ProcessSummaryCommand(origin: Origin, recipient: Recipient, private val summaries: AccountSummaryVoucher) :
            BasicCommand(origin, recipient) {
        override fun execute() {
            summaries.value().forEach { (name, record) ->
//                val key = CoraData.createPrimaryKey("name" to name)
//                val account: InteractiveAccount? = accounts[key].value()
//                if (account == null) createAccount(name, record) else updateAccount(account, record)
            }
        }
    }

}
