package com.coradec.apps.trader.com.impl

import com.coradec.apps.trader.com.DataRequest
import com.coradec.apps.trader.ibkr.model.HistoricalBarType
import com.coradec.apps.trader.ibkr.model.HistoricalBarType.*
import com.coradec.apps.trader.model.*
import com.coradec.coradeck.com.model.impl.BasicVoucher
import com.coradec.coradeck.core.model.Origin
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

class TitleDataRequestDaily(
    origin: Origin,
    val title: Title,
    val from: LocalDate,
    val upto: LocalDate,
    val barTypes: EnumSet<HistoricalBarType> = EnumSet.of(Trades, Bid, Ask, FeeRate, RebateRate)
) : BasicVoucher<Map<LocalDate, Quotes>>(origin), DataRequest {
    private val quotes = mutableMapOf<LocalDate, Quotes>()

    fun addQuotes(barType: HistoricalBarType, quoteseq: Sequence<DailyQuote>) {
        quoteseq.forEach { quote ->
            val timestamp = quote.daystamp
            val quotes = quotes.computeIfAbsent(timestamp) { Quotes(title, timestamp.atStartOfDay(ZoneId.systemDefault())) }
            when (barType) {
                Midpoint -> quotes.midpoints = quote.daily
                Bid -> quotes.bids = quote.daily
                Ask -> quotes.asks = quote.daily
                BidAsk -> quotes.bidAsks = quote.daily
                HistoricalVolatility -> quotes.historicalVolatilities = quote.daily
                OptionImpliedVolatility -> quotes.optionImpliedVolatilities = quote.daily
                FeeRate -> quotes.feeRates = quote.daily
                RebateRate -> quotes.rebateRates = quote.daily
                else -> throw IllegalArgumentException("Bar type ‹$barType› cannot be assigned with addQuotes()")
            }
        }
    }

    fun addQuantifiedQuotes(barType: HistoricalBarType, quoteseq: Sequence<DailyQuote>) {
        quoteseq.forEach { quote ->
            val timestamp = quote.daystamp
            val quotes = quotes.computeIfAbsent(timestamp) { Quotes(title, timestamp.atStartOfDay(ZoneId.systemDefault())) }
            when (barType) {
                Trades -> quotes.trades = quote.dailyQuantified
                else -> throw IllegalArgumentException("Bar type ‹$barType› cannot be assigned with addQuantifiedQuotes()")
            }
        }
    }

    fun seal() {
        value = quotes
        succeed()
    }

    private val DailyQuote.daily: Quote get() = Quote(open, high, low, close)
    private val DailyQuote.dailyQuantified: QuantifiedQuote get() =
        QuantifiedQuote(open, high, low, close, volume!!, weightedAveragePrice!!, count!!)
}
