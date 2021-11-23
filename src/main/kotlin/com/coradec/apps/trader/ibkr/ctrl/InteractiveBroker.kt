package com.coradec.apps.trader.ibkr.ctrl

import com.coradec.apps.trader.ibkr.com.AccountSummariesVoucher
import com.coradec.apps.trader.ibkr.com.CurrentTimeVoucher
import com.coradec.apps.trader.ibkr.com.GeneralNotification
import com.coradec.apps.trader.ibkr.com.InteractiveVoucher
import com.coradec.apps.trader.ibkr.com.event.CodedErrorEvent
import com.coradec.apps.trader.ibkr.com.event.ConnectAcknowledgedEvent
import com.coradec.apps.trader.ibkr.com.event.ManagedAccountsEvent
import com.coradec.apps.trader.ibkr.com.event.NextOrderIdEvent
import com.coradec.apps.trader.ibkr.com.req.HistoricalDataRequestDaily
import com.coradec.apps.trader.ibkr.model.AccountField
import com.coradec.apps.trader.ibkr.model.Accounts
import com.coradec.apps.trader.ibkr.model.HistoricalBarType
import com.coradec.apps.trader.model.Frequency
import com.coradec.apps.trader.model.Frequency.*
import com.coradec.apps.trader.model.Title
import com.coradec.coradeck.bus.model.impl.BasicBusEngine
import com.coradec.coradeck.core.util.here
import com.coradec.coradeck.ctrl.module.CoraControl
import com.coradec.coradeck.ctrl.module.subscribe
import com.coradec.coradeck.text.model.LocalText
import com.ib.client.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

typealias RequestId = Int

object InteractiveBroker : BasicBusEngine() {
    private val IMMEX = CoraControl.IMMEX
    private val signal = EJavaSignal()
    private val client: EClientSocket by lazy { EClientSocket(InteractiveResponseHandler, signal) }
    private val reader by lazy { EReader(client, signal) }
    private val nextRequest = AtomicInteger(0)
    private val next get() = nextRequest.getAndIncrement()

    private const val host = "localhost"
    private const val port = 10000
    private const val clientNr = 0

    private val TEXT_STARTING_SIGNAL_PROCESSOR = LocalText("StartingSignalProcessor")
    private val TEXT_TERMINATING_SIGNAL_PROCESSOR = LocalText("SignalProcessorTerminated")
    private val TEXT_STARTING_INTERACTIVE_BROKER = LocalText("StartingInteractiveBroker3")
    private val TEXT_FINALIZING_INTERACTIVE_BROKER = LocalText("FinalizingInteractiveBroker")
    private val TEXT_INTERACTIVE_ERROR = LocalText("InteractiveError2")
    private val TEXT_INTERACTIVE_EXCEPTION = LocalText("InteractiveException3")
    private val TEXT_INTERACTIVE_MESSAGE_ERROR = LocalText("InteractiveMessageError3")
    private val TEXT_INTERACTIVE_MESSAGE_EXCEPTION = LocalText("InteractiveMessageException4")

    override fun onInitializing() {
        super.onInitializing()
        info(TEXT_STARTING_INTERACTIVE_BROKER, clientNr, host, port)
        subscribe(ConnectAcknowledgedEvent::class, ManagedAccountsEvent::class, NextOrderIdEvent::class, GeneralNotification::class)
        subscribe(CodedErrorEvent::class)
        route(InteractiveVoucher::class, ::handleInteractiveVoucher)
        route(ConnectAcknowledgedEvent::class, ::connectionAcknowledged)
        route(ManagedAccountsEvent::class, ::managedAccounts)
        route(NextOrderIdEvent::class, ::nextOrderId)
        route(GeneralNotification::class, ::general)
        route(CurrentTimeVoucher::class, ::currentTime)
        route(CodedErrorEvent::class, ::errorOccurred)
        approve(HistoricalDataRequestDaily::class)
        client.eConnect(host, port, clientNr)
        reader.start()
    }

    override fun onFinalizing() {
        super.onFinalizing()
        client.eDisconnect()
        info(TEXT_FINALIZING_INTERACTIVE_BROKER)
    }

    override fun onStarted() {
        super.onStarted()
        SwitchBoard[Accounts::class] =
            accept(AccountSummariesVoucher(here, "All", EnumSet.allOf(AccountField::class.java))).content
        SwitchBoard.setTimeΔ(accept(CurrentTimeVoucher(here)).content.value)
    }

    override fun run() {
        info(TEXT_STARTING_SIGNAL_PROCESSOR)
        while (client.isConnected && !Thread.interrupted()) {
            signal.waitForSignal()
            reader.processMsgs()
        }
        debug("Interactive Broker was interrupted.")
        info(TEXT_TERMINATING_SIGNAL_PROCESSOR)
    }

    private fun errorOccurred(event: CodedErrorEvent) {
        val requestId = event.requestId
        val errorCode = event.errorCode
        val message = event.message
        val problem = event.problem
        when {
            message != null && problem != null -> error(TEXT_INTERACTIVE_MESSAGE_EXCEPTION, requestId, errorCode, message, problem)
            message != null -> error(TEXT_INTERACTIVE_MESSAGE_ERROR, requestId, errorCode, message)
            problem != null -> error(TEXT_INTERACTIVE_EXCEPTION, requestId, errorCode, problem)
            else -> error(TEXT_INTERACTIVE_ERROR, requestId, errorCode)
        }
    }

    private fun connectionAcknowledged(@Suppress("UNUSED_PARAMETER") event: ConnectAcknowledgedEvent) {
        SwitchBoard.connectionAcknowledged.set(true)
    }

    private fun managedAccounts(event: ManagedAccountsEvent) {
        event.accountList.forEach { SwitchBoard.addAccount(it) }
    }

    private fun nextOrderId(event: NextOrderIdEvent) {
        SwitchBoard.nextOrder.set(event.orderId)
    }

    private fun general(event: GeneralNotification) {
        // TODO we don't know handle to handle this yet.  It is already logged through the ResponseHandler
    }

    @Suppress("UNUSED_PARAMETER")
    private fun currentTime(voucher: CurrentTimeVoucher) {
        client.reqCurrentTime()
    }

    private fun handleInteractiveVoucher(voucher: InteractiveVoucher<*>) {
        voucher.request(client)
    }

    fun requestHistoricalData(
        title: Title,
        endTime: LocalDateTime,
        duration: String,
        frequency: Frequency,
        barType: HistoricalBarType,
        useRTH: Boolean = false,
        formatDate: Boolean = true,
        keepUpToDate: Boolean = false,
        chartOptions: List<TagValue> = emptyList()
    ): Int = next.apply {
        client.reqHistoricalData(
            this,
            title.contract,
            endTime.interactiveTS,
            duration,
            frequency.barSize,
            barType.code,
            if (useRTH) 1 else 0,
            if (formatDate) 1 else 0,
            keepUpToDate,
            chartOptions
        )
    }

    private val Title.contract: Contract get() = Contract().apply {
        symbol(name)
        secType(type.code)
        if (exchange != null) exchange(exchange) else exchange("SMART")
    }

    private val LocalDateTime.interactiveTS: String get() = format(DateTimeFormatter.ofPattern("yyyyMMdd hh:mm:ss"))

    private val Frequency.barSize: String get() = when(this) {
        s30 -> "30 sec"
        m1 -> "1 min"
        m5 -> "5 min"
        m15 -> "15 min"
        m30 -> "30 min"
        h1 -> "1 hour"
        h2 -> "1 hour"
        h4 -> "1 hour"
        D1 -> "1 day"
        W1 -> "1 day"
        M1 -> "1 day"
    }
}
