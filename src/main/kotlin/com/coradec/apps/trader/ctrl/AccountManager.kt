package com.coradec.apps.trader.ctrl

import com.coradec.apps.trader.ibkr.com.event.AccountSummaryPositionEvent
import com.coradec.apps.trader.ibkr.com.event.EndAccountSummaryEvent
import com.coradec.apps.trader.ibkr.com.event.ManagedAccountsEvent
import com.coradec.apps.trader.ibkr.com.event.TWSCurrentTimeEvent
import com.coradec.apps.trader.ibkr.ctrl.InteractiveBroker
import com.coradec.apps.trader.ibkr.model.Account
import com.coradec.apps.trader.ibkr.model.AccountField
import com.coradec.apps.trader.model.AccountDefinition
import com.coradec.apps.trader.model.AccountPurpose
import com.coradec.apps.trader.model.MoneyAmount
import com.coradec.apps.trader.model.impl.BasicAccountDefinition
import com.coradec.apps.trader.trouble.AccountNotFoundException
import com.coradec.coradeck.bus.model.impl.BasicBusNode
import com.coradec.coradeck.com.model.Notification
import com.coradec.coradeck.conf.model.LocalProperty
import com.coradec.coradeck.core.util.formatted
import com.coradec.coradeck.ctrl.module.subscribe
import com.coradec.coradeck.ctrl.module.unsubscribe
import com.coradec.coradeck.db.model.RecordTable
import com.coradec.coradeck.text.model.LocalText
import com.coradec.coradeck.type.module.CoraType
import java.time.Duration
import java.time.ZoneId
import java.util.*
import javax.swing.JOptionPane.*
import kotlin.reflect.full.isSubclassOf

@Suppress("PrivatePropertyName", "NonAsciiCharacters")
class AccountManager(private val accountTable: RecordTable<AccountDefinition>) : BasicBusNode() {
    private var summaryRequest: Int = 0
    private val candidates = mutableMapOf<Int, Account>()
    private val accounts = mutableMapOf<String, Account>()
    private var Δt = Duration.ZERO

    override fun accepts(notification: Notification<*>) = when (notification.content) {
        is ManagedAccountsEvent, is AccountSummaryPositionEvent, is EndAccountSummaryEvent, is TWSCurrentTimeEvent -> true
        else -> super.accepts(notification)
    }

    override fun onInitializing() {
        super.onInitializing()
        subscribe()
        route(ManagedAccountsEvent::class, ::accountReceived)
        route(AccountSummaryPositionEvent::class, ::receivePosition)
        route(EndAccountSummaryEvent::class, ::endPosition)
        route(TWSCurrentTimeEvent::class, ::timeDiff)
    }

    override fun onInitialized() {
        super.onInitialized()
        InteractiveBroker.requestCurrentTime()
        summaryRequest = InteractiveBroker.requestAccountSummary("All", EnumSet.allOf(AccountField::class.java).joinToString(","))
    }

    override fun onFinalizing() {
        super.onFinalizing()
        unsubscribe()
    }

    private fun accountReceived(event: ManagedAccountsEvent) {
        val accountList = accountTable.all.toSet()
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
                            QUESTION_MESSAGE
                        ).trim().uppercase()
                        purpose = AccountPurpose.valueOf(purposeStr)
                        break
                    } catch (e: Exception) {
                        showMessageDialog(null, "Enter «production» or «test»", "Invalid choice", ERROR_MESSAGE)
                    }
                accountTable += BasicAccountDefinition(account, purpose)
            }
        }
        accountTable.commit()
    }

    private fun receivePosition(event: AccountSummaryPositionEvent) {
        try {
            val field = AccountField.valueOf(event.tag)
            val currency = event.currency?.let { Currency.getInstance(it) } ?: PROP_DEFAULT_CURRENCY.value
            val value =
                if (field.type.isSubclassOf(MoneyAmount::class)) MoneyAmount(event.value.toDouble(), currency)
                else CoraType.castTo(event.value, field.type) as Any
            candidates.computeIfAbsent(event.requestId) { Account(event.account, mutableMapOf()) }.properties[field] = value
        } catch (e: Exception) {
            val ccy = event.currency?.let { " $it" } ?: ""
            error(e, TEXT_POSITION_PROBLEM, event.requestId, event.tag, event.value + ccy, event.account)
        }
    }

    private fun endPosition(event: EndAccountSummaryEvent) {
        val account = candidates.remove(event.requestId) ?: throw AccountNotFoundException("req#${event.requestId}")
        accounts.put(account.name, account)?.run {
            properties
                .map { Pair(it, account.properties[it.key]) }
                .mapNotNull {
                    when {
                        it.second == null -> "Property ${it.first.key} added: ${it.first.value.formatted}."
                        it.second != it.first.value -> "Property ${it.first.key} changed from ${it.second} to ${it.first.value}."
                        else -> null
                    }
                } + account.properties
                .map { Pair (it, properties[it.key]) }
                .mapNotNull {
                    if (it.second == null) "Property ${it.first.key} removed (was ${it.first.value}." else null
                }
                .run {
                    info(TEXT_ACCOUNT_UPDATED, account,name, joinToString ("\n• ", "\n• "))
                }
        } ?: info(TEXT_NEW_ACCOUNT, account.name)
    }

    private fun timeDiff(event: TWSCurrentTimeEvent) {
        Δt = Duration.between(event.createdAt, event.timestamp.atZone(ZoneId.systemDefault()))
        debug("TWS Time: %s, Δt = %s", event.timestamp, Δt)
    }

    companion object {
        val PROP_DEFAULT_CURRENCY = LocalProperty<Currency>("DefaultCurrency")
        val TEXT_ACCOUNT_UPDATED = LocalText("AccountUpdated2")
        val TEXT_NEW_ACCOUNT = LocalText("AccountCreated1")
        val TEXT_POSITION_PROBLEM = LocalText("PositionProblem4")
    }
}
