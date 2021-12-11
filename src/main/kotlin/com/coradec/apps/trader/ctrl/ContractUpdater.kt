package com.coradec.apps.trader.ctrl

import com.coradec.apps.trader.com.UpdateAnalyzersRequest
import com.coradec.apps.trader.com.impl.AnalysisEvent
import com.coradec.apps.trader.com.impl.TitleDataRequestDaily
import com.coradec.apps.trader.com.impl.UpdateContractRequest
import com.coradec.apps.trader.ibkr.com.event.CodedErrorEvent
import com.coradec.apps.trader.ibkr.com.event.HistoricalDataEndEvent
import com.coradec.apps.trader.ibkr.com.event.HistoricalDataEvent
import com.coradec.apps.trader.ibkr.ctrl.InteractiveBroker
import com.coradec.apps.trader.ibkr.model.HistoricalBarType
import com.coradec.apps.trader.ibkr.trouble.AmbiguousContractSpecificationException
import com.coradec.apps.trader.ibkr.trouble.MissingMarketDataException
import com.coradec.apps.trader.ibkr.trouble.UnhandledException
import com.coradec.apps.trader.model.*
import com.coradec.apps.trader.model.QuoteType.*
import com.coradec.apps.trader.model.impl.BasicDbQuote
import com.coradec.coradeck.bus.model.impl.BasicBusEngine
import com.coradec.coradeck.com.model.RequestState.*
import com.coradec.coradeck.com.model.Voucher
import com.coradec.coradeck.conf.model.LocalProperty
import com.coradec.coradeck.core.util.here
import com.coradec.coradeck.ctrl.module.subscribe
import com.coradec.coradeck.ctrl.module.unsubscribe
import com.coradec.coradeck.db.ctrl.impl.SqlSelection
import com.coradec.coradeck.db.model.RecordTable
import com.coradec.coradeck.text.model.LocalText
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.Semaphore

class ContractUpdater(
    val contracts: RecordTable<Title>,
    private val quotes: RecordTable<DbQuote>
) : BasicBusEngine() {
    private val historicalDataResponse = mutableMapOf<Int, TitleDataRequest>()
    private val updateRequests = LinkedBlockingQueue<TitleDataRequest>()
    private val updateSemaphore = Semaphore(50) // number of parallel history data requests allowed by Interactive Broker
    private val insertions = mutableMapOf<Int, MutableList<Voucher<Int>>>()

    override fun onInitializing() {
        route(UpdateContractRequest::class, ::updateTitle)
        route(TitleDataRequestDaily::class, ::updateDailyTitle)
        route(HistoricalDataEvent::class, ::addDataEvent)
        route(HistoricalDataEndEvent::class, ::dispatchDataRequest)
        route(UpdateAnalyzersRequest::class, ::updateAnalyzers)
        route(CodedErrorEvent::class, ::fillInError)
        route(AnalysisEvent::class, ::analysisAvailable)
        subscribe()
        super.onInitializing()
    }

    override fun onFinalizing() {
        unsubscribe()
        super.onFinalizing()
    }

    override fun run() {
        while (!Thread.interrupted()) {
            updateSemaphore.acquire()
            accept(updateRequests.take()).content.whenFinished {
                updateSemaphore.release()
                when (state) {
                    SUCCESSFUL -> info(TEXT_SUCCESSFUL, this)
                    FAILED -> error(reason, TEXT_FAILED, this)
                    CANCELLED -> error(reason, TEXT_CANCELLED, this)
                    LOST -> error(reason, TEXT_LOST, this)
                    else -> updateSemaphore.acquire() // shouldn't happen
                }
            }
        }
    }

    private fun addDataEvent(event: HistoricalDataEvent) {
        val bar = event.bar
        val requestId = event.requestId
        val request: TitleDataRequest = historicalDataResponse[requestId]
            ?: throw IllegalStateException("Request[$requestId]: no pending response!")
        val title = request.title
        val volume = bar.volume().longValue()
        val wap = bar.wap().value().toDouble()
        val barstamp = request.parse(bar.time())
        debug(
            "Request[%d]: Handling historical data event @%s of type ‹%s› for title «%s»",
            requestId,
            barstamp,
            request.type,
            title.name
        )
        if (barstamp in request)
            insertions.computeIfAbsent(requestId) { ArrayList(request.capacity) } += quotes.insert(BasicDbQuote(
                title.name, barstamp, request.type, bar.open(), bar.high(), bar.low(), bar.close(), volume, wap, bar.count()
            ))
    }

    private fun dispatchDataRequest(event: HistoricalDataEndEvent) {
        quotes.standby()
        val requestId = event.requestId
        val inserted = insertions.remove(requestId)?.sumOf { it.value } ?: 0
        debug("Request[%d]: Historical data complete: %d records inserted.", requestId, inserted)
        val request: TitleDataRequest = historicalDataResponse.remove(requestId)
            ?: throw IllegalStateException("Request[$requestId]: no pending response!")
        request.succeed()
        PROP_ANALYZERS.value.forEach {
            if (request.type == it.source) accept(UpdateAnalyzersRequest(here, request.title, it))
        }
    }

    private fun fillInError(event: CodedErrorEvent) {
        val requestId = event.requestId
        val response = historicalDataResponse[requestId]
        if (response == null) {
            error(TEXT_FREE_FLOATING_ERROR, event)
        } else {
            val title = response.title
            val quoteType = response.type
            when (event.errorCode) {
                200 -> {
                    error(TEXT_AMBIGUOUS_CONTRACT, title)
                    response.fail(AmbiguousContractSpecificationException(event.message, title))
                }
                162 -> {
                    error(TEXT_MISSING_MARKET_DATA, title, quoteType)
                    response.fail(MissingMarketDataException(event.message, title, quoteType))
                }
                else -> {
                    error(TEXT_UNKNOWN_PROBLEM, quoteType, title, event.message ?: "(no further information)")
                    response.fail(UnhandledException(event.message, title, quoteType))
                }
            }
        }
    }

    private fun updateTitle(request: UpdateContractRequest) {
        try {
            val title = request.title
            debug("Updating title ‹%s› with frequency ‹%s›.", title.name, title.frequency.label)
            when (title.frequency) {
                Frequency.s30 -> TODO()
                Frequency.m1 -> TODO()
                Frequency.m5 -> TODO()
                Frequency.m15 -> TODO()
                Frequency.m30 -> TODO()
                Frequency.h1 -> TODO()
                Frequency.h2 -> TODO()
                Frequency.h4 -> TODO()
                Frequency.D1 -> updateDaily(title)
                Frequency.W1 -> TODO()
                Frequency.M1 -> TODO()
            }
            debug("Updated title ‹%s›.", title.name)
            request.succeed()
        } catch (e: Exception) {
            error(e)
            request.fail(e)
        }
    }

    private fun updateDaily(title: Title) {
        val quoteTypes = EnumSet.of(Trades, Bid, Ask, FeeRate, RebateRate)
        quoteTypes.forEach { quoteType ->
            // make sure we have at least the last 100 days
            val startDate = LocalDate.now().minusDays(101)
            val from = startDate.minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            val fromString = "to_timestamp('$from', 'YYYY-MM-DD')"
            val fromDay: LocalDate = title.lastUpdated?.toLocalDate()?.plusDays(1) ?: quotes
                .select(SqlSelection("[titleRef='${title.name}'] and [type=${quoteType.ordinal}] and [timeStamp>$fromString]"))
                .map { it.timeStamp.toLocalDate() }
                .ifEmpty { sequenceOf(startDate) }
                .last()
            val requiredDates = mutableSetOf<LocalDate>()
            var currentDate = fromDay
            do {
                requiredDates += currentDate
                currentDate = currentDate.plusDays(1)
            } while (currentDate <= LocalDate.now())
            // requiredDates now contains the days for which we need data
            if (requiredDates.isNotEmpty()) updateRequests += TitleDataRequestDaily(here, title, requiredDates, quoteType)
        }
    }

    private fun updateDailyTitle(request: TitleDataRequestDaily) {
        val title = request.title
        val from = request.first
        val upto = request.last
        val freq = title.frequency
        val quoteType = request.type
        val barType = quoteType.toBarType
        val duration = when (title.frequency) {
            Frequency.D1 -> "%s D".format(ChronoUnit.DAYS.between(from, upto).coerceAtLeast(1))
            Frequency.W1 -> "%s D".format(ChronoUnit.DAYS.between(from, upto).coerceAtLeast(1))
            Frequency.M1 -> "%s M".format(ChronoUnit.MONTHS.between(from, upto).coerceAtLeast(1))
            else -> throw IllegalArgumentException("Invalid daily frequency: ${title.frequency}")
        }
        val requestId = InteractiveBroker.requestHistoricalData(title, upto.atStartOfDay(), duration, freq, barType)
        historicalDataResponse[requestId] = request
        debug("Request[$requestId]: requesting %s of %s history data for title ‹%s›.", duration, barType.name, title.name)
    }

    private fun updateAnalyzers(request: UpdateAnalyzersRequest) {
        val title = request.title
        val frequency = title.frequency
        val analyzer = request.analyzer
        val source = when (frequency) {
            Frequency.s30 -> TODO()
            Frequency.m1 -> TODO()
            Frequency.m5 -> TODO()
            Frequency.m15 -> TODO()
            Frequency.m30 -> TODO()
            Frequency.h1 -> TODO()
            Frequency.h2 -> TODO()
            Frequency.h4 -> TODO()
            Frequency.D1 -> quotes
            Frequency.W1 -> quotes
            Frequency.M1 -> TODO()
        }
        debug("Updating %s ‹%s› on «%s».", frequency.labelly, analyzer.name, title.name)
        try {
            analyzer.updateDB(title, frequency, source)
            request.succeed()
        } catch (e: Exception) {
            request.fail(e)
        }
    }

    private fun analysisAvailable(event: AnalysisEvent) {
        debug("Received analysis: $event")
    }

    private val QuoteType.toBarType: HistoricalBarType
        get() = when (this) {
            Trades -> HistoricalBarType.Trades
            Midpoint -> HistoricalBarType.Midpoint
            Bid -> HistoricalBarType.Bid
            Ask -> HistoricalBarType.Ask
            BidAsk -> HistoricalBarType.BidAsk
            HistoricalVolatility -> HistoricalBarType.HistoricalVolatility
            OptionImpliedVolatility -> HistoricalBarType.OptionImpliedVolatility
            FeeRate -> HistoricalBarType.FeeRate
            RebateRate -> HistoricalBarType.RebateRate
        }

    companion object {
        val PROP_ANALYZERS = LocalProperty<List<Analyzer>>("Analyzers")

        val TEXT_SUCCESSFUL = LocalText("Successful1")
        val TEXT_FAILED = LocalText("Failed1")
        val TEXT_CANCELLED = LocalText("Cancelled1")
        val TEXT_LOST = LocalText("Lost1")
        val TEXT_AMBIGUOUS_CONTRACT = LocalText("AmbiguousContract1")
        val TEXT_MISSING_MARKET_DATA = LocalText("MissingMarketData2")
        val TEXT_UNKNOWN_PROBLEM = LocalText("UnknownProblem3")
        val TEXT_FREE_FLOATING_ERROR = LocalText("FreeFloatingError1")
    }
}
