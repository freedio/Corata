package com.coradec.apps.trader.ctrl

import com.coradec.apps.trader.com.impl.TitleDataRequestDaily
import com.coradec.apps.trader.com.impl.UpdateTitleRequest
import com.coradec.apps.trader.model.DbQuote
import com.coradec.apps.trader.model.Frequency.*
import com.coradec.apps.trader.model.QuoteType.*
import com.coradec.apps.trader.model.Title
import com.coradec.apps.trader.model.impl.BasicDbQuote
import com.coradec.apps.trader.model.impl.BasicTitle
import com.coradec.coradeck.bus.model.BusNode
import com.coradec.coradeck.bus.model.impl.BasicBusHub
import com.coradec.coradeck.bus.view.BusContext
import com.coradec.coradeck.conf.model.LocalProperty
import com.coradec.coradeck.core.util.here
import com.coradec.coradeck.ctrl.module.CoraControl
import com.coradec.coradeck.ctrl.module.subscribe
import com.coradec.coradeck.ctrl.module.unsubscribe
import com.coradec.coradeck.db.ctrl.impl.SqlSelection
import com.coradec.coradeck.db.model.Database
import com.coradec.coradeck.db.model.RecordTable
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

class TitleUpdater(val db: Database) : BasicBusHub() {
    private lateinit var dayQuotes: RecordTable<DbQuote>
    private lateinit var titles: RecordTable<Title>

    override fun onAttached(context: BusContext) {
        route(UpdateTitleRequest::class, ::updateTitle)
        subscribe(UpdateTitleRequest::class)
        super.onAttached(context)
    }

    override fun onInitializing() {
        super.onInitializing()
        add("RequestHandler", requestHandler.value.createInstance().memberView)
        dayQuotes = db.openTable(BasicDbQuote::class)
        titles = db.openTable(BasicTitle::class)
    }

    override fun onFinalized() {
        unsubscribe()
        unroute(UpdateTitleRequest::class)
        super.onFinalized()
    }

    private fun updateTitle(request: UpdateTitleRequest) {
        try {
            val title = request.title
            debug("Updating title ‹%s› with frequency ‹%s›.", title.name, title.frequency.label)
            when (title.frequency) {
                s30 -> TODO()
                m1 -> TODO()
                m5 -> TODO()
                m15 -> TODO()
                m30 -> TODO()
                h1 -> TODO()
                h2 -> TODO()
                h4 -> TODO()
                D1 -> updateDaily(title)
                W1 -> TODO()
                M1 -> TODO()
            }
            debug("Updated title ‹%s›.", title.name)
            request.succeed()
        } catch (e: Exception) {
            error(e)
            request.fail(e)
        }
    }

    private fun updateDaily(title: Title) {
        // make sure we have at least the last 100 days
        val startDate = LocalDate.now().minusDays(101)
        val from = startDate.minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        val fromString = "to_timestamp('$from', 'YYYY-MM-DD')"
        val requiredDates = mutableSetOf<LocalDate>()
        var currentDate = startDate
        do {
            requiredDates += currentDate
            currentDate = currentDate.plusDays(1)
        } while (currentDate < LocalDate.now())
        requiredDates -= dayQuotes
            .select(SqlSelection("[titleRef='${title.name}'] and [daystamp>$fromString]"))
            .map { it.daystamp }
            .toSet()
        // requiredDates now contains the days for which we need data
        debug("Missing dates for title ‹%s›: %s", title.name, requiredDates)
        if (requiredDates.isEmpty()) return
        val first = requiredDates.first()
        val last = requiredDates.last().plusDays(1)
        debug("Creating DAILY title ‹%s› data request.", title.name)
        IMMEX.inject(TitleDataRequestDaily(here, title, first, last)).content.value
            .filterKeys { day -> day in requiredDates }
            .forEach { (day, quotes) -> with (quotes) {
                quotes.trades?.apply {
                    dayQuotes += BasicDbQuote(title.name, day, Trades, open, high, low, close, volume, weightedAveragePrice, count)
                }
                quotes.midpoints?.apply {
                    dayQuotes += BasicDbQuote(title.name, day, Midpoint, open, high, low, close)
                }
                quotes.asks?.apply {
                    dayQuotes += BasicDbQuote(title.name, day, Ask, open, high, low, close)
                }
                quotes.bids?.apply {
                    dayQuotes += BasicDbQuote(title.name, day, Bid, open, high, low, close)
                }
                quotes.bidAsks?.apply {
                    dayQuotes += BasicDbQuote(title.name, day, BidAsk, open, high, low, close)
                }
                quotes.historicalVolatilities?.apply {
                    dayQuotes += BasicDbQuote(title.name, day, HistoricalVolatility, open, high, low, close)
                }
                quotes.optionImpliedVolatilities?.apply {
                    dayQuotes += BasicDbQuote(title.name, day, OptionImpliedVolatility, open, high, low, close)
                }
                quotes.feeRates?.apply {
                    dayQuotes += BasicDbQuote(title.name, day, FeeRate, open, high, low, close)
                }
                quotes.rebateRates?.apply {
                    dayQuotes += BasicDbQuote(title.name, day, RebateRate, open, high, low, close)
                }
            }
        }
    }

    companion object {
        val IMMEX = CoraControl.IMMEX
        val requestHandler = LocalProperty<KClass<BusNode>>("RequestHandler")
    }
}
