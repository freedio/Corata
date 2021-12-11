package com.coradec.apps.trader.ibkr.com.req

import com.coradec.apps.trader.com.impl.TitleDataRequestDaily
import com.coradec.apps.trader.ibkr.com.InteractiveRequest
import com.coradec.apps.trader.ibkr.com.event.CodedErrorEvent
import com.coradec.apps.trader.ibkr.com.event.HistoricalDataEndEvent
import com.coradec.apps.trader.ibkr.com.event.HistoricalDataEvent
import com.coradec.apps.trader.ibkr.ctrl.RequestId
import com.coradec.apps.trader.ibkr.model.HistoricalBarType
import com.coradec.apps.trader.ibkr.trouble.AmbiguousContractSpecificationException
import com.coradec.apps.trader.ibkr.trouble.MissingMarketDataException
import com.coradec.apps.trader.ibkr.trouble.UnhandledException
import com.coradec.apps.trader.model.Quote
import com.coradec.apps.trader.model.QuoteType
import com.coradec.apps.trader.model.impl.BasicQuote
import com.coradec.coradeck.com.model.Information
import com.coradec.coradeck.com.model.Notification
import com.coradec.coradeck.core.model.Origin
import com.coradec.coradeck.core.trouble.MultiException
import com.coradec.coradeck.core.util.asLocalDate
import com.coradec.coradeck.core.util.relax
import com.coradec.coradeck.ctrl.module.subscribe
import com.coradec.coradeck.ctrl.module.unsubscribe
import com.ib.client.Bar

class HistoricalDataRequestDaily(
    origin: Origin,
    private val request: TitleDataRequestDaily,
    private val quoteType: QuoteType
) : InteractiveRequest(origin) {
    private val bars = mutableListOf<Bar>()
    private val barType = quoteType.toBarType
    private val problems = mutableListOf<Exception>()
    var requestId: RequestId = 0
    private val title = request.title
//    private val from = request.from
//    private val upto = request.upto
//    private val duration
//        get() = when (title.frequency) {
//            D1 -> "%s D".format(Period.between(from, upto).days.coerceAtLeast(1))
//            W1 -> "%s D".format(Period.between(from, upto).days.coerceAtLeast(1))
//            M1 -> "%s M".format(Period.between(from, upto).years.coerceAtLeast(1))
//            else -> throw IllegalArgumentException("Invalid daily frequency: ${title.frequency}")
//        }

    override fun execute() {
        subscribe()
//        requestId = InteractiveBroker.requestHistoricalData(title, upto.atStartOfDay(), duration, title.frequency, barType)
        debug("Request[$requestId]: requesting %s history data for title ‹%s›.", barType.name, title.name)
    }

    override fun accepts(information: Information) = when (information) {
        is HistoricalDataEvent -> information.requestId == requestId
        is HistoricalDataEndEvent -> information.requestId == requestId
        is CodedErrorEvent -> information.requestId == requestId
        else -> false
    }

    override fun receive(notification: Notification<*>) = when (val event = notification.content) {
        is HistoricalDataEvent -> if (event.requestId == requestId) {
            bars += event.bar
        } else relax()
        is HistoricalDataEndEvent -> if (event.requestId == requestId) {
            debug("Request[$requestId]: Received ${bars.size} history data entries for title $title.")
//            request.reportQuotes(bars.associate { bar ->
//                Pair(bar.toDay(), bar.toQuote())
//            })
            unsubscribe()
            if (problems.isNotEmpty()) {
//                request.reportProblems(problems.asSequence())
                fail(MultiException(problems))
            } else succeed()
        } else relax()
        is CodedErrorEvent -> if (event.requestId == requestId) {
            when (event.errorCode) {
                200 -> {
                    with (AmbiguousContractSpecificationException(event.message, title)) {
//                        request.reportProblem(this)
                        fail(this)
                    }
                }
                162 -> {
                    with (MissingMarketDataException(event.message, title, quoteType)) {
//                        request.reportProblem(this)
                        fail(this)
                    }
                }
                else -> {
                    problems += UnhandledException(event.message, title, quoteType)
                }
            }
        } else relax()
        else -> ignoreInformation(event)
    }

    private fun Bar.toQuote(): Quote =
        BasicQuote(open(), high(), low(), close(), volume().longValue(), wap().value().toDouble(), count())

    private fun Bar.toDay() = time().asLocalDate("yyyyMMdd")

    private val QuoteType.toBarType: HistoricalBarType get() = when (this) {
        QuoteType.Trades -> HistoricalBarType.Trades
        QuoteType.Midpoint -> HistoricalBarType.Midpoint
        QuoteType.Bid -> HistoricalBarType.Bid
        QuoteType.Ask -> HistoricalBarType.Ask
        QuoteType.BidAsk -> HistoricalBarType.BidAsk
        QuoteType.HistoricalVolatility -> HistoricalBarType.HistoricalVolatility
        QuoteType.OptionImpliedVolatility -> HistoricalBarType.OptionImpliedVolatility
        QuoteType.FeeRate -> HistoricalBarType.FeeRate
        QuoteType.RebateRate -> HistoricalBarType.RebateRate
    }
}
