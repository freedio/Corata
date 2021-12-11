package com.coradec.apps.trader.ibkr.ctrl

import com.coradec.apps.trader.ibkr.com.GeneralNotification
import com.coradec.apps.trader.ibkr.com.InteractiveVoucher
import com.coradec.apps.trader.ibkr.com.event.ConnectAcknowledgedEvent
import com.coradec.apps.trader.ibkr.com.event.NextOrderIdEvent
import com.coradec.apps.trader.ibkr.model.HistoricalBarType
import com.coradec.apps.trader.model.Frequency
import com.coradec.apps.trader.model.Frequency.*
import com.coradec.apps.trader.model.Title
import com.coradec.coradeck.bus.model.impl.BasicBusEngine
import com.coradec.coradeck.ctrl.module.CoraControl
import com.coradec.coradeck.ctrl.module.subscribe
import com.coradec.coradeck.text.model.LocalText
import com.ib.client.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.atomic.AtomicInteger

typealias RequestId = Int

object InteractiveBroker : BasicBusEngine() {
    private val IMMEX = CoraControl.IMMEX
    private val signal = EJavaSignal()
    private val client: EClientSocket by lazy { EClientSocket(InteractiveResponseHandler, signal) }
    private val reader by lazy { EReader(client, signal) }
    private val nextRequest = AtomicInteger(0)
    val next get() = nextRequest.incrementAndGet()

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

    override fun onInitialized() {
        super.onInitialized()
        info(TEXT_STARTING_INTERACTIVE_BROKER, clientNr, host, port)
        subscribe()
        route(InteractiveVoucher::class, ::handleInteractiveVoucher)
        route(ConnectAcknowledgedEvent::class, ::connectionAcknowledged)
        route(NextOrderIdEvent::class, ::nextOrderId)
        route(GeneralNotification::class, ::general)
        client.eConnect(host, port, clientNr)
        reader.start()
    }

    override fun onFinalizing() {
        super.onFinalizing()
        client.eDisconnect()
        info(TEXT_FINALIZING_INTERACTIVE_BROKER)
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

    private fun connectionAcknowledged(@Suppress("UNUSED_PARAMETER") event: ConnectAcknowledgedEvent) {
        SwitchBoard.connectionAcknowledged.set(true)
    }

    private fun nextOrderId(event: NextOrderIdEvent) {
        SwitchBoard.nextOrder.set(event.orderId)
    }

    private fun general(event: GeneralNotification) {
        // TODO we don't know handle to handle this yet.  It is already logged through the ResponseHandler
    }

    private fun handleInteractiveVoucher(voucher: InteractiveVoucher<*>) {
        voucher.listen(IMMEX)
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

    fun requestAccountSummary(group: String, fields: String): Int = next.apply {
        debug("Requesting account summary for group «%s» and fields ‹%s›.", group, fields)
        client.reqAccountSummary(this, group, fields)
    }

    fun requestCurrentTime() {
        debug("Requesting the current time.")
        client.reqCurrentTime()
    }

    private val Title.contract: Contract
        get() = Contract().apply {
            symbol(name)
            secType(type.code)
            if (exchange != null) exchange(exchange) else exchange("SMART")
            if (primExch != null) primaryExch(primExch)
        }

    private val LocalDateTime.interactiveTS: String get() = format(DateTimeFormatter.ofPattern("yyyyMMdd hh:mm:ss"))

    private val Frequency.barSize: String
        get() = when (this) {
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
