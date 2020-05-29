/*
 * Copyright © 2019 by Coradec LLC.  All rights reserved.
 */

package com.coradec.coratrade.interactive.ctrl

import com.coradec.coradeck.com.ctrl.Observer
import com.coradec.coradeck.com.ctrl.TypedObserver
import com.coradec.coradeck.com.model.Information
import com.coradec.coradeck.core.ctrl.Origin
import com.coradec.coradeck.core.util.here
import com.coradec.coradeck.core.util.toBit
import com.coradec.coradeck.ctrl.com.impl.BasicRequest
import com.coradec.coradeck.ctrl.com.impl.BasicVoucher
import com.coradec.coradeck.ctrl.ctrl.impl.BasicAgent
import com.coradec.coradeck.ctrl.module.CoraControl
import com.coradec.coradeck.text.model.LocalizedText
import com.coradec.coradeck.type.model.GenericType
import com.coradec.coratrade.interactive.comm.ctrl.impl.ConnectionVoucher
import com.coradec.coratrade.interactive.comm.ctrl.impl.CurrentTimeVoucher
import com.coradec.coratrade.interactive.comm.ctrl.impl.ManagedAccountsVoucher
import com.coradec.coratrade.interactive.comm.ctrl.impl.OrderIdVoucher
import com.coradec.coratrade.interactive.comm.model.trouble.ErrorEvent
import com.coradec.coratrade.interactive.comm.model.trouble.ExceptionEvent
import com.coradec.coratrade.interactive.comm.model.trouble.RequestErrorEvent
import com.coradec.coratrade.interactive.comm.model.trouble.impl.ApiErrorEvent
import com.coradec.coratrade.interactive.comm.model.trouble.impl.TwsErrorEvent
import com.coradec.coratrade.interactive.model.AccountField
import com.coradec.coratrade.interactive.model.HistoryDataSelection
import com.coradec.coratrade.interactive.trouble.RequestFailure
import com.coradec.coratrade.interactive.trouble.TwsException
import com.ib.client.Contract
import com.ib.client.EClientSocket
import com.ib.client.EJavaSignal
import com.ib.client.EReader
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit.MILLISECONDS
import java.util.concurrent.TimeUnit.SECONDS
import java.util.concurrent.atomic.AtomicInteger

object RequestDispatcher : BasicAgent(), Observer {
    private val TEXT_UNKNOWN_EVENT = LocalizedText.define("UnknownEvent")
    private val TEXT_STARTING_COMMAND_DISPATCHER = LocalizedText.define("StartingCommandDispatcher")
    private val TEXT_SHUTTING_DOWN_COMMAND_DISPATCHER = LocalizedText.define("ShuttingDownCommandDispatcher")
    private val TEXT_SIGNAL_PROCESSOR_STARTED = LocalizedText.define("SignalProcessorStarted")
    private val TEXT_SIGNAL_PROCESSOR_ENDED = LocalizedText.define("SignalProcessorTerminated")
    private val TEXT_RELAX_AFTER_REQUEST= LocalizedText.define("RelaxAfterRequest")

    private val IBDateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss zzz")
    private var requestId = AtomicInteger(0)
    private var lastRequest: Long = 0
    val nextRequestId: Int get() = requestId.getAndIncrement()
    private val signal = EJavaSignal()
    private val client by lazy { EClientSocket(ResponseDispatcher, signal) }
    private val reader by lazy { EReader(client, signal) }
    private val processor = Thread(::process, "SignalProcessor")
    private lateinit var connection : ConnectionVoucher
    val connected: Boolean get() = connection.value()
    lateinit var managedAccounts: ManagedAccountsVoucher
    lateinit var serverTimeLag: CurrentTimeVoucher
    lateinit var initialOrderId: OrderIdVoucher
    private val nextOrderId: AtomicInteger by lazy { AtomicInteger(initialOrderId.value()) }
    val orderId: Int get() = nextOrderId.getAndIncrement()
    private val mq get() = CoraControl.messageQueue

    init {
        info(TEXT_STARTING_COMMAND_DISPATCHER)
        registerInitialObservers()
        addRoute(IBrokerRequest::class.java, ::handleRequest)
        addRoute(IBrokerVoucher::class.java, ::handleVoucher)
        client.eConnect("localhost", 10000, 0)
        reader.start()
        while (!connected) Thread.sleep(100)
        requestCurrentTime(serverTimeLag)
        processor.isDaemon = true
        processor.start()
        SECONDS.sleep(1)
        mq.subscribe(this, ApiErrorEvent::class.java, TwsErrorEvent::class.java)
    }

    private fun registerInitialObservers() {
        connection = ConnectionVoucher(here)
        mq.subscribe(connection)
        managedAccounts = ManagedAccountsVoucher(here)
        mq.subscribe(managedAccounts)
        initialOrderId = OrderIdVoucher(here)
        mq.subscribe(initialOrderId)
        serverTimeLag = CurrentTimeVoucher(here)
    }

    override fun notify(info: Information): Boolean = when (info) {
        is ApiErrorEvent -> killEngine()
        is TwsErrorEvent -> killEngine()
        else -> false
    }

    private fun killEngine(): Boolean {
        processor.interrupt()
        shutdown()
        return true
    }

    private fun handleVoucher(voucher: IBrokerVoucher<*>) = voucher.trigger()

    private fun handleRequest(request: IBrokerRequest) {
        TODO("Don't know yet how to handle this!")
    }

    private fun process() {
        info(TEXT_SIGNAL_PROCESSOR_STARTED)
        while (client.isConnected && !Thread.interrupted()) {
            signal.waitForSignal()
            reader.processMsgs()
        }
        info(TEXT_SIGNAL_PROCESSOR_ENDED)
    }

    fun shutdown() {
        waitAtLeastOneSecondAfterLastRequest()
        info(TEXT_SHUTTING_DOWN_COMMAND_DISPATCHER, client.twsConnectionTime)
        client.eDisconnect()
        MILLISECONDS.sleep(100)
    }

    private fun waitAtLeastOneSecondAfterLastRequest() {
        with(lastRequest + 1000 - System.currentTimeMillis()) {
            if (this > 0) {
                info(TEXT_RELAX_AFTER_REQUEST, this)
                Thread.sleep(this)
            }
        }
    }

    private fun commandOn(observer: Observer) {
        mq.subscribe(observer, ErrorEvent::class.java)
        lastRequest = System.currentTimeMillis()
    }

    private fun requestOn(observer: Observer) {
        mq.subscribe(observer, RequestErrorEvent::class.java)
        lastRequest = System.currentTimeMillis()
    }

    fun requestNextOrderId(observer: Observer) {
        commandOn(observer)
        client.reqIds(0)
    }

    fun requestManagedAccounts(observer: Observer) {
        commandOn(observer)
        client.reqManagedAccts()
    }

    fun requestCurrentTime(observer: Observer) {
        commandOn(observer)
        client.reqCurrentTime()
    }

    fun requestAccountSummaries(
            observer: Observer,
            requestId: Int,
            account: String,
            fields: EnumSet<AccountField>
    ) {
        requestOn(observer)
        client.reqAccountSummary(requestId, account, fields.joinToString(","))
    }

    fun requestSymbolQuery(observer: Observer, requestId: Int, pattern: String) {
        requestOn(observer)
        client.reqMatchingSymbols(requestId, pattern)
    }

    fun requestOpenOrders(observer: Observer, all: Boolean) {
        commandOn(observer)
        if (all) client.reqAllOpenOrders() else client.reqOpenOrders()
    }

    fun requestHistoricalData(observer: Observer, tickerId: Int, contract: Contract, selection: HistoryDataSelection) {
        requestOn(observer)
        debug("Sending HistoricalDataRequest(tickerId=$tickerId, contract=$contract)")
        client.reqHistoricalData(
                tickerId,
                contract,
                selection.endDateTime.format(IBDateFormat),
                selection.duration.toString(),
                selection.barSize.toString(),
                selection.subject.name,
                selection.regularTradingHoursOnly.toBit(),
                2 - selection.formattedDates.toBit(),
                selection.continuous,
                selection.chartOptions
        )
    }

    fun requestContractDetails(observer: Observer, requestId: Int, contract: Contract) {
        requestOn(observer)
        client.reqContractDetails(requestId, contract)
    }

    fun requestSecurityDefinitionOptionParameters(
            observer: Observer,
            requestId: Int,
            underlying: String,
            futFopExchange: String,
            underlyingSecType: String,
            underlyingConId: Int
            ) {
        requestOn(observer)
        client.reqSecDefOptParams(requestId, underlying, futFopExchange, underlyingSecType, underlyingConId)
    }

    abstract class IBrokerRequest(origin: Origin, urgent: Boolean, createdAt: LocalDateTime, id: UUID) :
            BasicRequest(origin, RequestDispatcher, urgent, createdAt, id)

    abstract class IBrokerVoucher<V>(
            type: GenericType<V>,
            origin: Origin,
            urgent: Boolean = false,
            createdAt: LocalDateTime = LocalDateTime.now(),
            id: UUID = UUID.randomUUID()
    ) : BasicVoucher<V>(origin, RequestDispatcher, type, urgent, createdAt, id), TypedObserver {
        abstract fun trigger()
        override fun notify(info: Information): Boolean {
            var finished = false
            if (info is ExceptionEvent) {
                debug("Request failed with %s", info)
                fail(info.exception)
                finished = true
            } else if (info is ApiErrorEvent && info.exception != null) {
                debug("Request failed with %s", info)
                fail(info.exception)
                finished = true
            } else if (info is ErrorEvent) {
                debug("Request failed with %s", info)
                fail(TwsException(info.errorCode, info.errorMessage))
                finished = true
            } else warn(TEXT_UNKNOWN_EVENT, info)
            return finished
        }
    }

    abstract class IBrokerRequestVoucher<V>(
            type: GenericType<V>,
            origin: Origin,
            urgent: Boolean = false,
            createdAt: LocalDateTime = LocalDateTime.now(),
            id: UUID = UUID.randomUUID()
    ) :
            IBrokerVoucher<V>(type, origin, urgent, createdAt, id), TypedObserver {
        protected val requestId: Int = nextRequestId
        override fun notify(info: Information): Boolean =
                if (info is RequestErrorEvent && info.requestId == requestId) {
                    debug("Request failed with %s", info)
                    fail(RequestFailure(info.errorCode, info.errorMessage))
                    true
                } else super.notify(info)
    }

}
