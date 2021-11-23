package com.coradec.apps.trader.ctrl

import com.coradec.apps.trader.ibkr.com.event.ManagedAccountsEvent
import com.coradec.apps.trader.model.AccountDefinition
import com.coradec.apps.trader.model.AccountPurpose
import com.coradec.apps.trader.model.impl.BasicAccountDefinition
import com.coradec.coradeck.bus.model.impl.BasicBusNode
import com.coradec.coradeck.ctrl.module.subscribe
import com.coradec.coradeck.ctrl.module.unsubscribe
import com.coradec.coradeck.db.model.Database
import com.coradec.coradeck.db.model.RecordTable
import javax.swing.JOptionPane
import javax.swing.JOptionPane.showInputDialog
import javax.swing.JOptionPane.showMessageDialog

class AccountManager(val db: Database): BasicBusNode() {
    private lateinit var accounts: RecordTable<AccountDefinition>

    override fun onInitializing() {
        super.onInitializing()
        accounts = db.openTable(BasicAccountDefinition::class)
        subscribe(ManagedAccountsEvent::class)
        route(ManagedAccountsEvent::class, ::accountReceived)
    }

    override fun onFinalizing() {
        super.onFinalizing()
        unsubscribe()
        accounts.close()
    }

    private fun accountReceived(event: ManagedAccountsEvent) {
        val accountList = accounts.all.toSet()
        debug("Received accounts: %s.", accountList)
        event.accountList.forEach { account ->
            var purpose: AccountPurpose
            if (account !in accountList.map { it.name }) {
                while (true)
                    try {
                        val purposeStr = showInputDialog(
                            null,
                            "Purpose of Account «$account» (productive|test)?",
                            "Unknown Account Purpose",
                            JOptionPane.QUESTION_MESSAGE
                        ).trim().uppercase()
                        purpose = AccountPurpose.valueOf(purposeStr)
                        break
                    } catch (e: Exception) {
                        showMessageDialog(null, "Enter «production» or «test»", "Invalid choice", JOptionPane.ERROR_MESSAGE)
                    }
                accounts += BasicAccountDefinition(account, purpose)
            }
        }
    }

}
