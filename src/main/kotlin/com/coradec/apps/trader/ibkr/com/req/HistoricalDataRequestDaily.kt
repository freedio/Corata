package com.coradec.apps.trader.ibkr.com.req

import com.coradec.apps.trader.com.impl.TitleDataRequestDaily
import com.coradec.apps.trader.ibkr.com.InteractiveRequest
import com.coradec.apps.trader.ibkr.com.event.CodedErrorEvent
import com.coradec.apps.trader.ibkr.com.event.HistoricalDataEndEvent
import com.coradec.apps.trader.ibkr.com.event.HistoricalDataEvent
import com.coradec.apps.trader.ibkr.ctrl.InteractiveBroker
import com.coradec.apps.trader.ibkr.ctrl.RequestId
import com.coradec.apps.trader.ibkr.model.HistoricalBarType
import com.coradec.apps.trader.ibkr.model.HistoricalBarType.Trades
import com.coradec.apps.trader.ibkr.trouble.AmbiguouContractSpecificationException
import com.coradec.apps.trader.ibkr.trouble.MissingMarketDataException
import com.coradec.apps.trader.model.DailyQuote
import com.coradec.apps.trader.model.Frequency.*
import com.coradec.apps.trader.model.QuoteType
import com.coradec.apps.trader.model.impl.BasicDailyQuote
import com.coradec.coradeck.com.model.Notification
import com.coradec.coradeck.com.module.CoraCom
import com.coradec.coradeck.core.model.Origin
import com.coradec.coradeck.core.model.Priority.B3
import com.coradec.coradeck.core.util.asLocalDate
import com.coradec.coradeck.core.util.relax
import com.coradec.coradeck.ctrl.module.subscribe
import com.coradec.coradeck.ctrl.module.unsubscribe
import com.ib.client.Bar
import java.time.LocalDate
import java.time.Period

class HistoricalDataRequestDaily(
    origin: Origin,
    private val voucher: TitleDataRequestDaily,
    private val barType: HistoricalBarType
) : InteractiveRequest(origin, B3) {
    private val bars = mutableListOf<Bar>()
    private var requestId: RequestId = 0
    private val title = voucher.title
    private val from = voucher.from
    private val upto = voucher.upto
    private val duration
        get() = when (title.frequency) {
            D1 -> "%s D".format(Period.between(from, upto).days.coerceAtLeast(1))
            W1 -> "%s D".format(Period.between(from, upto).days.coerceAtLeast(1))
            M1 -> "%s M".format(Period.between(from, upto).years.coerceAtLeast(1))
            else -> throw IllegalArgumentException("Invalid daily frequency: ${title.frequency}")
        }

    override fun execute() {
        debug("Requesting %s history data for title ‹%s›.", barType.name, title.name)
        subscribe(HistoricalDataEvent::class, HistoricalDataEndEvent::class, CodedErrorEvent::class)
        requestId = InteractiveBroker.requestHistoricalData(title, upto.atStartOfDay(), duration, title.frequency, barType)
    }

    override fun receive(notification: Notification<*>) = when (val event = notification.content) {
        is HistoricalDataEvent -> if (event.requestId == requestId) {
            bars += event.bar
        } else relax()
        is HistoricalDataEndEvent -> if (event.requestId == requestId) {
            debug("Received ${bars.size} history data entries for title $title.")
            unsubscribe()
            if (barType == Trades) voucher.addQuantifiedQuotes(barType, bars.toQuantifiedQuotes())
            else voucher.addQuotes(barType, bars.toQuotes())
            succeed()
        } else relax()
        is CodedErrorEvent -> if (event.requestId == requestId) {
            CoraCom.log.detail("Received error with code %d!", event.errorCode)
            when (event.errorCode) {
                200 -> fail(AmbiguouContractSpecificationException(event.message))
                162 -> fail(MissingMarketDataException(event.message))
                else -> relax()
            }
        } else relax()
        else -> ignoreInformation(event)
    }

    private fun MutableList<Bar>.toQuotes(): Sequence<DailyQuote> = asSequence().map { bar ->
        BasicDailyQuote(title, bar.day(), barType.toQuoteType(), bar.open(), bar.high(), bar.low(), bar.close())
    }

    private fun MutableList<Bar>.toQuantifiedQuotes(): Sequence<DailyQuote> = asSequence().map { bar ->
        BasicDailyQuote(
            title, bar.day(), barType.toQuoteType(), bar.open(), bar.high(), bar.low(), bar.close(), bar.volume().longValue(),
            bar.wap().value().toDouble(), bar.count()
        )
    }

    private fun Bar.day(): LocalDate = time().asLocalDate("yyyyMMdd")
    private fun HistoricalBarType.toQuoteType(): QuoteType = QuoteType.valueOf(name)
}
