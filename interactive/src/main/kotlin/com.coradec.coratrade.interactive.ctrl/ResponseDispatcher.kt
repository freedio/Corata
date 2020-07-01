/*
 * Copyright © 2019 by Coradec LLC.  All rights reserved.
 */

package com.coradec.coratrade.interactive.ctrl

import com.coradec.coradeck.core.util.caller
import com.coradec.coradeck.ctrl.ctrl.impl.BasicAgent
import com.coradec.coradeck.text.model.LocalizedText
import com.coradec.coratrade.interactive.comm.model.impl.*
import com.coradec.coratrade.interactive.comm.model.trouble.impl.*
import com.coradec.coratrade.interactive.model.*
import com.ib.client.*
import java.net.SocketException
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

private val Bar.repr get() = "Bar@%s open=%.2f, close=%.2f, low=%.2f, high=%.2f, volume=%d, count=%d, wap=%.2f".format(
        time(), open(), close(), low(), high(), volume(), count(), wap())

object ResponseDispatcher : BasicAgent(), EWrapper {
    private val TEXT_TWS_REQUEST_ERROR = LocalizedText.define("TwsRequestError")
    private val TEXT_TWS_ERROR = LocalizedText.define("TwsError")
    private val TEXT_CLIENT_ERROR = LocalizedText.define("ClientError")
    private val TEXT_TWS_REQUEST_WARNING = LocalizedText.define("TwsRequestWarning")
    private val TEXT_TWS_WARNING = LocalizedText.define("TwsWarning")
    private val TEXT_TWS_REQUEST_INFO = LocalizedText.define("TwsRequestInfo")
    private val TEXT_TWS_INFORMATION = LocalizedText.define("TwsInformation")
    private val TEXT_TWS_CONNECTION_ERROR = LocalizedText.define("TwsConnectionError")
    private val TEXT_API_ERROR1 = LocalizedText.define("ApiError")
    private val TEXT_API_ERROR2 = LocalizedText.define("ApiErrorMessage")

    override fun tickByTickAllLast(
            requestId: Int,
            tickType: Int,
            systime: Long,
            price: Double,
            size: Int,
            tickAttribLast: TickAttribLast,
            exchange: String,
            specialConditions: String
    ) {
        val timestamp = Instant.ofEpochMilli(systime).atZone(ZoneId.systemDefault())
        debug("Request[%d]: receiving type %d tick-by-tick realtime data on %s from \"%s\" at %f with size %d, attributes {%s} and special conditions \"%s\".", requestId, tickType, timestamp, exchange, price, size, tickAttribLast, specialConditions)
        inject(LastTickEvent(this, requestId, LastTick.of(tickType, exchange, timestamp, price, size, tickAttribLast, specialConditions)))
    }

    override fun tickByTickBidAsk(
            requestId: Int,
            systime: Long,
            bidPrice: Double,
            askPrice: Double,
            bidSize: Int,
            askSize: Int,
            tickAttribBidAsk: TickAttribBidAsk
    ) {
        val timestamp = Instant.ofEpochMilli(systime).atZone(ZoneId.systemDefault())
        debug("Request[%d]: receiving bid/ask tick-by-tick realtime data on %s at bid:%f / ask:%f with bid size:%d / ask size:%d and attributes {%s}.", requestId, timestamp, bidPrice, askPrice, bidSize, askSize, tickAttribBidAsk)
        inject(BidAskEvent(this, requestId, BidAskTick.of(timestamp, bidPrice, bidSize, askPrice, askSize, tickAttribBidAsk)))
    }

    override fun tickByTickMidPoint(
            requestId: Int,
            systime: Long,
            midPoint: Double
    ) {
        val timestamp = Instant.ofEpochMilli(systime).atZone(ZoneId.systemDefault())
        debug("Request[%d]: receiving mid-point tick-by-tick realtime data on %s at mid-point %f.",
                requestId, timestamp, midPoint)
        inject(TickByTickMidPointEvent(this, requestId, MidPointTick.of(timestamp, midPoint)))
    }

    override fun tickEFP(
            tickerId: Int,
            tickType: Int,
            basisPoints: Double,
            formattedBasisPoints: String,
            impliedFuture: Double,
            holdDays: Int,
            futureLastTradeDate: String,
            dividendImpact: Double,
            dividendsToLastTradeDate: Double
    ) {
        debug("Ticker[%d]: receiving exchange of physicals data of tick type %d: annualized basis points %f(\"%s\"), implied future price %f with %d hold days, future expiration data %s, dividend impact %f and expected dividend to last trade date %f until expiration.",
                tickerId, tickType, basisPoints, formattedBasisPoints, impliedFuture, holdDays, futureLastTradeDate,
                dividendImpact, dividendsToLastTradeDate)
        inject(EFPTickEvent(this, tickerId, EFPTick.of(tickType, basisPoints, formattedBasisPoints, impliedFuture, holdDays,
                futureLastTradeDate, dividendImpact, dividendsToLastTradeDate)))
    }

    override fun tickGeneric(
            tickerId: Int,
            field: Int,
            value: Double
    ) {
        debug("Ticker[%d]: receiving generic ticker data for field %d (${TickType.getField(field)}) with value %f.",
                tickerId, field, value)
        inject(GenericTickEvent(this, tickerId, GenericTick.of(field, value)))
    }

    override fun tickNews(
            tickerId: Int,
            systime: Long,
            providerCode: String,
            articleId: String,
            headline: String,
            extraData: String
    ) {
        val timestamp = Instant.ofEpochMilli(systime).atZone(ZoneId.systemDefault())
        debug("Ticker[%d]: receiving tick news on %s from provider \"%s\": article \"%s\" with headline \"%s\" (\"%s\").",
                tickerId, timestamp, providerCode, articleId, headline, extraData)
        inject(NewsTickEvent(this, tickerId, NewsTick.of(timestamp, providerCode, articleId, headline, extraData)))
    }

    override fun tickOptionComputation(
            tickerId: Int,
            field: Int,
            impliedVolatility: Double,
            delta: Double,
            price: Double,
            pwDividend: Double,
            gamma: Double,
            vega: Double,
            theta: Double,
            undPrice: Double
    ) {
        debug("Ticker[%d]: receiving option specific market data for option type %d (${TickType.getField(field)}) with implied volatility %f, option delta %f, price %f, underlying dividend %f, gamma %f, vega %f, theta %f and underlying price %f.",
                tickerId, field, impliedVolatility, delta, price, pwDividend, gamma, vega, theta, undPrice)
        inject(OptionComputationTickEvent(this, tickerId,
                OptionComputationTick.of(field, impliedVolatility, delta, price, pwDividend, gamma, vega, theta, undPrice)))
    }

    override fun tickPrice(
            tickerId: Int,
            field: Int,
            price: Double,
            attributes: TickAttrib
    ) {
        debug("Ticker[%d]: receiving tick price for field %d (${TickType.getField(field)}), actual price %f and attributes \"%s\".",
                tickerId, field, price, attributes)
        inject(PriceTickEvent(this, tickerId, PriceTick.of(field, price, attributes)))
    }

    override fun tickSize(
            tickerId: Int,
            field: Int,
            size: Int
    ) {
        debug("Ticker[%d]: size of field %d (${TickType.getField(field)}) = %d.", tickerId, field, size)
        inject(SizeTickEvent(this, tickerId, SizeTick.of(field, size)))
    }

    override fun tickReqParams(
            tickerId: Int,
            minTick: Double,
            bboExchange: String,
            snapshotPermissions: Int
    ) {
        debug("Ticker[%d]: minimal tick size: %f, BBO exchange \"%s\", snapshot permissions: %d.")
        inject(RequestParametersTickEvent(this, tickerId, RequestParameterTick.of(minTick, bboExchange, snapshotPermissions)))
    }

    override fun tickString(tickerId: Int, field: Int, value: String) {
        debug("Ticker[%d]: receiving tick string \"%s\" for field %d (${TickType.getField(field)})", tickerId, value, field)
        inject(StringTickEvent(this, StringTick.of(field, value)))
    }

    override fun tickSnapshotEnd(tickerId: Int) {
        debug("Ticker[%d]: End of snapshots.", tickerId)
        inject(EndSnapshotEvent(this, tickerId))
    }

    override fun historicalData(requestId: Int, bar: Bar) {
        debug("Request[%d]: receiving historical data %s.", requestId, bar.repr)
        inject(HistoricalDataEvent(this, requestId, bar))
    }

    override fun historicalDataEnd(requestId: Int, start: String, end: String) {
        debug("Request[%d]: end of historical data from %s to %s.", requestId, start, end)
        inject(EndHistoricalDataEvent(this, requestId, start, end))
    }

    override fun historicalDataUpdate(requestId: Int, bar: Bar) {
        debug("Request[%d]: receiving historical data update %s.", requestId, bar)
        inject(HistoricalDataUpdateEvent(this, requestId, bar))
    }

    override fun historicalNews(requestId: Int, time: String, providerCode: String, articleId: String, headline: String) {
        debug("Request[%d]: receiving historical news article \"%s\" from %s on %s with headline \"%s\".",
                requestId, time, providerCode, articleId, headline)
        inject(HistoricalNewsevent(this, requestId, News.from(time, providerCode, articleId, headline)))
    }

    override fun historicalNewsEnd(requestId: Int, moreAvailable: Boolean) {
        debug("Request[%d]: end of historical news%s.", requestId, if (moreAvailable) " (more available)" else "")
        inject(EndHistoricalNewsEvent(this, requestId, moreAvailable))
    }

    override fun historicalTicks(requestId: Int, ticks: MutableList<HistoricalTick>, done: Boolean) {
        debug("Request[%d]: receiving historical ticks %s%s.", requestId, ticks, if (done) " (last quote)" else "")
        inject(HistoricalTicksEvent(this, requestId, ticks))
        if (done) inject(EndHistoricalTicksEvent(this, requestId))
    }

    override fun historicalTicksBidAsk(requestId: Int, ticks: MutableList<HistoricalTickBidAsk>, done: Boolean) {
        debug("Request[%d]: receiving history mid/ask ticks %s%s.", requestId, ticks, if (done) " (last quote)" else "")
        inject(HistoricalBidAskTicksEvent(this, requestId, ticks))
        if (done) inject(EndHistoricalBidAskTicksEvent(this, requestId))
    }

    override fun historicalTicksLast(requestId: Int, ticks: MutableList<HistoricalTickLast>, done: Boolean) {
        debug("Request[%d]: receiving history last value ticks %s%s.", requestId, ticks, if (done) " (last quote)" else "")
        inject(HistoricalLastTicksEvent(this, requestId, ticks))
        if (done) inject(EndHistoricalLastTicksEvent(this, requestId))
    }

    override fun mktDepthExchanges(depthMarketDataDescriptions: Array<out DepthMktDataDescription>) {
        val rewrapped = depthMarketDataDescriptions.toList()
        debug("Receiving Depth Market Data Descriptions %s.", rewrapped)
        inject(MarketDepthExchangesEvent(this, rewrapped))
    }

    override fun histogramData(requestId: Int, data: MutableList<HistogramEntry>) {
        debug("Request[%d]: receiving histogram data %s.", requestId, data)
        inject(HistogramDataEvent(this, requestId, data))
    }

    override fun updateAccountValue(key: String, value: String, currency: String?, accountId: String) {
        debug("Account[%s]: setting %s to \"%s%s\".", accountId, value, if (currency != null) " $currency" else "")
        inject(AccountValueUpdateEvent(this, accountId, key, value, currency))
    }

    override fun updateAccountTime(timestamp: String) {
        debug("Account[?]: last updated on %s.", timestamp)
        inject(AccountTimeUpdateEvent(this, timestamp))
    }

    override fun accountDownloadEnd(accountId: String) {
        debug("Account[%s]: download finished.")
        inject(EndAccountDownloadEvent(this, accountId))
    }

    override fun accountUpdateMulti(
            requestId: Int,
            accountId: String?,
            modelCode: String?,
            key: String,
            value: String,
            currency: String?
    ) {
        debug("Request[%d] (Account[%s] / model[%s]): setting %s to \"%s%s\".",
                requestId, accountId ?: "???", modelCode ?: "???", key, value, if (currency != null) " $currency" else "")
        inject(MultiAccountUpdateEvent(this, requestId, AccountModelUpdate.of(accountId, modelCode, key, value, currency)))
    }

    override fun accountUpdateMultiEnd(requestId: Int) {
        debug("Request[%d]: Multi account update finished.")
        inject(EndMultiAccountUpdateEvent(this, requestId))
    }

    override fun openOrder(orderId: Int, contract: Contract, order: Order, orderState: OrderState) {
        debug("Order[%d]: Open order %s for contract %s with state %s.", orderId, order, contract, orderState)
        inject(OpenOrderEvent(this, orderId, ContractedOrder.of(contract, order, orderState)))
    }

    override fun openOrderEnd() {
        debug("End of open orders.")
        inject(EndOpenOrdersEvent(this))
    }

    override fun orderBound(orderId: Long, apiClientId: Int, apiOrderId: Int) {
        debug("Bound local order id %d from client %s to permanent order id %d.", apiOrderId, apiClientId, orderId)
        inject(OrderBoundEvent(this, orderId, apiClientId, apiOrderId))
    }

    override fun orderStatus(
            orderId: Int,
            status: String,
            filled: Double,
            remaining: Double,
            avgFillPrice: Double,
            permId: Int,
            parentId: Int,
            lastFillPrice: Double,
            clientId: Int,
            holdingReason: String,
            mktCapPrice: Double
    ) {
        debug("Order[%d]: status of permanent order %d on client %d with parent order %d changed to \"%s\" with filled price %f, remaining positions: %f, average fill price %f, last fill price %f, holdingReason \"%s\" and market cap price %f",
                orderId, permId, clientId, parentId, status, filled, remaining, avgFillPrice, lastFillPrice, holdingReason,
                mktCapPrice)
        inject(OrderStatusEvent(this, orderId,
                TradeOrderStatus.of(status, filled, remaining, avgFillPrice, lastFillPrice, holdingReason, mktCapPrice)))
    }

    override fun connectAck() {
        debug("Connection acknowledged.")
        inject(ConnectionAcknowledgedEvent(this))
    }

    override fun updateMktDepth(
            tickerId: Int,
            position: Int,
            operation: Int,
            side: Int,
            price: Double,
            size: Int
    ) {
        debug("Ticker[%d]: receiving market depth update for position %d, %s operation on %s side: price = %f, size = %d.",
                tickerId, position, opCode(operation), sideCode(side), price, size)
        inject(MarketLevel1DepthUpdateEvent(this, tickerId, MarketDepthUpdate.of(position, operation, side, price, size)))
    }

    override fun updateMktDepthL2(
            tickerId: Int,
            position: Int,
            marketMaker: String,
            operation: Int,
            side: Int,
            price: Double,
            size: Int,
            smartDepth: Boolean
    ) {
        debug("Ticker[%d]: receiving level 2 market update for position %d, %s operation on %s side: price = %f, size = %d on %s %s%s.",
                tickerId, position, opCode(operation), sideCode(side), price, size,
                if (smartDepth) "exchange" else "market maker", marketMaker, if (smartDepth) " (smart depth)" else "")
        inject(MarketLevel2DepthUpdateEvent(this, tickerId,
                MarketDepthUpdate.of(marketMaker, position, operation, side, price, size, smartDepth)))
    }

    override fun receiveFA(faDataType: Int, faXmlData: String) {
        debug("Receiving financial advisor's configuration of type %d as \"%s\".", faDataType, faXmlData)
        inject(FinancialAdvisorEvent(this, faDataType, faXmlData))
    }

    override fun nextValidId(orderId: Int) {
        debug("Next valid order ID: %d.", orderId)
        inject(NextValidOrderIdEvent(this, orderId))
    }

    override fun updatePortfolio(
            contract: Contract,
            position: Double,
            marketPrice: Double,
            marketValue: Double,
            averageCost: Double,
            unrealizedPNL: Double,
            realizedPNL: Double,
            accountName: String
    ) {
        debug("Receiving portfolio of account \"%s\": contract %s: position %f, market price %f, market value %f, average cost %f, unrealized PNL: %f, realized PNL: %f.",
                accountName, contract, position, marketPrice, marketValue, averageCost, unrealizedPNL, realizedPNL)
        inject(PortfolioUpdateEvent(this, accountName,
                Portfolio.of(contract, position, marketPrice, marketValue, averageCost, unrealizedPNL, realizedPNL)))
    }

    override fun securityDefinitionOptionalParameter(
            requestId: Int,
            exchange: String,
            underlyingConId: Int,
            tradingClass: String,
            multiplier: String,
            expirations: MutableSet<String>,
            strikes: MutableSet<Double>
    ) {
        debug("Request[%d]: receiving security definition option parameters for underlying contract %d on exchange %s with option trading class \"%s\" and multiplier \"%s\": expirations=%s, possible strikes=%s.",
                requestId, underlyingConId, exchange, tradingClass, multiplier, expirations, strikes)
        inject(SecurityDefinitionOptionParameterEvent(this, requestId,
                SecurityDefinitionOptionParameter.of(exchange, underlyingConId, tradingClass, multiplier, expirations, strikes)))
    }

    override fun securityDefinitionOptionalParameterEnd(requestId: Int) {
        debug("Request[%d]: End of security definition optional parameter.", requestId)
        inject(SecurityDefinitionOptionParameterEndEvent(this, requestId))
    }

    override fun connectionClosed() {
        debug("Connection closed.")
        inject(ConnectionClosedEvent(this))
    }

    override fun pnl(requestId: Int, dailyPnL: Double, unrealizedPnL: Double, realizedPnL: Double) {
        debug("Request[%d]: PnL update: daily = %f, whereof realized: %f, unrealized: %f.",
                requestId, dailyPnL, unrealizedPnL, realizedPnL)
        inject(ProfitAndLossEvent(this, requestId, ProfitAndLoss.of(dailyPnL, unrealizedPnL, realizedPnL)))
    }

    override fun pnlSingle(
            requestId: Int,
            position: Int,
            dailyPnL: Double,
            unrealizedPnL: Double,
            realizedPnL: Double,
            value: Double
    ) {
        debug("Request[%d]: daily PnL %f (realized %f, unrealized %f) with market value %f for position of size %d.")
        inject(ProfitAndLossEvent(this, requestId, ProfitAndLoss.of(dailyPnL, unrealizedPnL, realizedPnL, position, value)))
    }

    override fun familyCodes(familyCodes: Array<out FamilyCode>) {
        val refined = familyCodes.toList()
        debug("Receiving family codes %s.", refined)
        inject(FamilyCodeEvent(this, refined))
    }

    override fun accountSummary(requestId: Int, accountId: String, tag: String, value: String, currency: String?) {
        debug("Request[%d] (Account[%s] Summary): %s=\"%s%s\".",
                requestId, accountId, tag, value, if (currency != null) " $currency" else "")
        inject(AccountSummaryEvent(this, requestId, AccountSummary.of(accountId, tag, value, currency)))
    }

    override fun accountSummaryEnd(requestId: Int) {
        debug("Request[%d] (Account[???] Summary): end.", requestId)
        inject(EndAccoutSummaryEvent(this, requestId))
    }

    override fun rerouteMktDataReq(requestId: Int, conId: Int, exchange: String) {
        debug("Request[%d]: CFD market data conId = %d on exchange \"%s\".", requestId, conId, exchange)
        inject(MarketDataRerouteEvent(this, requestId, MarketDataRequestReroute.of(conId, exchange)))
    }

    override fun rerouteMktDepthReq(requestId: Int, conId: Int, exchange: String) {
        debug("Request[%d]: Level 2 data conId = %d on exchange \"%s\".")
        inject(MarketDataRerouteEvent(this, requestId, MarketDataRequestReroute.of2(conId, exchange)))
    }

    override fun error(problem: Exception) {
        if (problem !is SocketException && RequestDispatcher.connected) {
            inject(ApiErrorEvent(caller, exception = problem))
            error(problem, TEXT_API_ERROR1)
        }
    }

    override fun error(problem: String) {
        inject(ApiErrorEvent(caller, message = problem))
        error(TEXT_API_ERROR2, problem)
    }

    override fun error(requestId: Int, errorCode: Int, errorMsg: String) {
        when (errorCode) {
            in 1100..1999 -> {
                inject(ConnectionErrorEvent(caller, errorCode, errorMsg))
                error(TEXT_TWS_CONNECTION_ERROR, errorCode, errorMsg)
            }
            2104, 2106, 2158 ->
                if (requestId == -1) info(TEXT_TWS_INFORMATION, errorCode, errorMsg)
                else info(TEXT_TWS_REQUEST_INFO, errorCode, requestId, errorMsg)
            in 2100..2999 ->
                if (requestId == -1) warn(TEXT_TWS_WARNING, errorCode, errorMsg)
                else warn(TEXT_TWS_REQUEST_WARNING, errorCode, requestId, errorMsg)
            in 500..504 -> {
                inject(ClientErrorEvent(caller, errorCode, errorMsg))
                error(TEXT_CLIENT_ERROR, errorCode, errorMsg)
            }
            else ->
                if (requestId == -1) {
                    inject(TwsErrorEvent(caller, errorCode, errorMsg))
                    error(TEXT_TWS_ERROR, errorCode, errorMsg)
                }
                else {
                    inject(TwsRequestErrorEvent(caller, requestId, errorCode, errorMsg))
                    error(TEXT_TWS_REQUEST_ERROR, errorCode, requestId, errorMsg)
                }
        }
    }

    override fun newsArticle(requestId: Int, articleType: Int, articleText: String) {
        debug("Request[%d]: Receiving news article of type %d: \"$%s\".", requestId, articleType, articleText)
        inject(NewsArticleEvent(this, requestId, NewsArticle.of(articleType, articleText)))
    }

    override fun newsProviders(newsProviders: Array<out NewsProvider>) {
        val refined = newsProviders.toList()
        debug("Receiving list of news providers: %s.", refined)
        inject(NewsProvidersEvent(this, refined))
    }

    override fun updateNewsBulletin(messageId: Int, messageType: Int, message: String, origExch: String) {
        debug("Bulletin[%d]: receiving message \"%s\" of type %d (\"%s\") from original exchange %s.",
                messageId, message, messageType, decodeMessageType(messageType), origExch)
        inject(NewsBulletinUpdateEvent(this, NewsBulletin.of(messageId, messageType, message, origExch)))
    }

    override fun contractDetails(requestId: Int, contractDetails: ContractDetails) {
        debug("Request[%d]: Receiving contract details %s.", requestId, contractDetails)
        inject(ContractDetailsEvent(this, requestId, contractDetails))
    }

    override fun contractDetailsEnd(requestId: Int) {
        debug("Request[%d]: End of contract details.", requestId)
        inject(EndContractDetailsEvent(this, requestId))
    }

    override fun softDollarTiers(requestId: Int, tiers: Array<out SoftDollarTier>) {
        val refined = tiers.toList()
        debug("Request[%d]: receiving soft dollar tiers %s.", requestId, refined)
        inject(SoftDollarTiersEvent(this, requestId, refined))
    }

    override fun headTimestamp(requestId: Int, headTimestamp: String) {
        debug("Request[%d]: receiving head timestamp \"%s\".", requestId, headTimestamp)
        inject(HeadTimestampEvent(this, requestId, headTimestamp))
    }

    override fun bondContractDetails(requestId: Int, contractDetails: ContractDetails) {
        debug("Request[%d]: Receiving bond contract details %s.", requestId, contractDetails)
        inject(BondContractDetailsEvent(this, requestId, contractDetails))
    }

    override fun currentTime(time: Long) {
        val localTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(time), ZoneId.systemDefault())
        debug("Current server time: %s (raw: %d).", localTime, time)
        inject(CurrentTimeEvent(this, localTime))
    }

    override fun position(account: String, contract: Contract, positions: Double, avgCost: Double) {
        debug("Position information for contract %s of account %s: %f positions of total avg cost %f.",
                contract, account, positions, avgCost)
        inject(PositionEvent(this, Position.of(contract, positions, avgCost)))
    }

    override fun positionEnd() {
        debug("End of position report.")
        inject(EndPositionsEvent(this))
    }

    override fun positionMulti(
            requestId: Int,
            account: String,
            modelCode: String,
            contract: Contract,
            positions: Double,
            avgCost: Double
    ) {
        debug("Request[%d]: Open position for contract %s of model %s on account %s: %f positions of average costs %f.",
                requestId, contract, modelCode, account, positions, avgCost)
        inject(MultiPositionEvent(this, requestId, Position.of(contract, positions, avgCost, modelCode)))
    }

    override fun positionMultiEnd(requestId: Int) {
        debug("Request[%d]: End of positions", requestId)
        inject(EndMultiPositionsEvent(this, requestId))
    }

    override fun verifyCompleted(successful: Boolean, errorText: String?) {
        debug("Verify completed %s.", if (successful) "successfully" else "with error \"$errorText\"")
        inject(VerifyCompletedEvent(this, successful, errorText))
    }

    override fun verifyAndAuthCompleted(successful: Boolean, errorText: String) {
        debug("Verify and auth completed %s.", if (successful) "successfully" else "with error \"$errorText\"")
        inject(VerifyCompletedEvent(this, successful, errorText))
        inject(AuthCompletedEvent(this, successful, errorText))
    }

    override fun verifyMessageAPI(apiData: String) {
        debug("Verify message API: \"%s\".", apiData)
        inject(MessageAPIVerifyCompletedEvent(this, apiData))
    }

    override fun verifyAndAuthMessageAPI(apiData: String, xyzChallenge: String) {
        debug("Verify and auth message API: apiData=\"$%s\", xyz challenge \"$%s\".", apiData, xyzChallenge)
        inject(MessageAPIVerifyCompletedEvent(this, apiData))
        inject(MessageAPIAuthCompletedEvent(this, apiData, xyzChallenge))
    }

    override fun scannerData(
            requestId: Int,
            rank: Int,
            contractDetails: ContractDetails,
            distance: String,
            benchmark: String,
            projection: String,
            combolegs: String
    ) {
        debug("Request[%d]: receiving market scanner result: rank: %d, contract details: %s, distance = %s, benchmark: %s, projection: %s, combo legs: %s.", requestId, rank, contractDetails, distance, benchmark, projection, combolegs)
        inject(ScannerDataEvent(this, requestId,
                ScannerData.of(rank, contractDetails, distance, benchmark, projection, combolegs)))
    }

    override fun scannerDataEnd(requestId: Int) {
        debug("Request[%d]: End of market scanner data.", requestId)
        inject(EndScannerDataEvent(this, requestId))
    }

    override fun scannerParameters(parameterXml: String) {
        debug("Scanner parameters: \"%s\"", parameterXml)
        inject(ScannerParametersEvent(this, parameterXml))
    }

    override fun fundamentalData(requestId: Int, data: String) {
        debug("Request[%d]: receiving fundamental data \"%s\"", requestId, data)
        inject(FundamentalDataEvent(this, requestId, data))
    }

    override fun smartComponents(requestId: Int, exchAbbr: MutableMap<Int, MutableMap.MutableEntry<String, Char>>) {
        debug("Request[%d]: dictionary of exchanges and their abbreviations: %s.", requestId, exchAbbr)
        inject(SmartComponentEvent(this, requestId, exchAbbr))
    }

    override fun deltaNeutralValidation(requestId: Int, deltaNeutralContract: DeltaNeutralContract) {
        debug("Request[%d]: receiving delta neutral contract %s.", requestId, deltaNeutralContract)
        inject(DeltaNeutralValidationEvent(this, requestId, deltaNeutralContract))
    }

    override fun managedAccounts(accountList: String) {
        debug("Managed accounts: %s.", accountList)
        inject(ManagedAccountsEvent(this, accountList.split(',').toSet()))
    }

    override fun symbolSamples(requestId: Int, contractDescrs: Array<out ContractDescription>) {
        val refined = contractDescrs.toList()
        debug("Request[%d]: receiving sample contract definitions %s.", requestId, refined)
        inject(SymbolSamplesEvent(this, requestId, refined))
    }

    override fun marketDataType(requestId: Int, marketDataType: Int) {
        debug("Request[%d]: Market data type is %d.", requestId, marketDataType)
        inject(MarketDataTypeEvent(this, requestId, marketDataType))
    }

    override fun displayGroupList(requestId: Int, groups: String) {
        val refined = groups.split('|')
        debug("Request[%d]: receiving display group list %s.", refined)
        inject(DisplayGroupsEvent(this, refined))
    }

    override fun displayGroupUpdated(requestId: Int, contractInfo: String) {
        debug("Request[%d]: contract info \"%s\" has changed in display group.", requestId, contractInfo)
        inject(DisplayGroupUpdateEvent(this, requestId, contractInfo))
    }

    override fun marketRule(marketRuleId: Int, priceIncrements: Array<out PriceIncrement>) {
        val refined = priceIncrements.toList()
        debug("Price increments for market rule %d: %s.", marketRuleId, refined)
        inject(MarketruleEvent(this, marketRuleId, refined))
    }

    override fun execDetails(requestId: Int, contract: Contract, execution: Execution) {
        debug("Request[%d]: receiving execution %s for contract %s.", requestId, execution, contract)
        inject(ExecutionDetailsEvent(this, requestId, contract, execution))
    }

    override fun execDetailsEnd(requestId: Int) {
        debug("Request[%d]: all executions received.")
        inject(EndExecutionDetailsEvent(this, requestId))
    }

    override fun realtimeBar(requestId: Int, time: Long, open: Double, high: Double, low: Double, close: Double, volume: Long, wap: Double, count: Int) {
        val timestamp = Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault())
        debug("Request[%d]: receiving 5 seconds bar for system timestamp $%s: %d trades @ open %f, high %f, low %f, close %f, wap %f, volume %d.", requestId, timestamp, count, open, high, low, close, wap, volume)
        inject(RealTimeBarEvent(this, requestId, RealtimeBar.of(timestamp, open, high, low, close, volume, wap, count)))
    }

    override fun commissionReport(commissionReport: CommissionReport) {
        debug("Receiving commission report %s.", commissionReport)
        inject(CommissionReportEvent(this, commissionReport))
    }

    private fun sideCode(side: Int): String = when (side) {
        0 -> "ask"
        1 -> "bid"
        else -> "unknown"
    }

    private fun opCode(operation: Int): String = when (operation) {
        0 -> "insert"
        1 -> "update"
        2 -> "delete"
        else -> "unknown"
    }

    private fun decodeMessageType(messageType: Int): String = when (messageType) {
        1 -> "regular message"
        2 -> "exchange no longer available for trading"
        3 -> "exchange available for trading"
        else -> "unknown"
    }

}
