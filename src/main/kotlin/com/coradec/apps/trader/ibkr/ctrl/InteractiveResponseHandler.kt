package com.coradec.apps.trader.ibkr.ctrl

import com.coradec.apps.trader.ibkr.com.GeneralNotification
import com.coradec.apps.trader.ibkr.com.GetCurrentAccountVoucher
import com.coradec.apps.trader.ibkr.com.event.*
import com.coradec.apps.trader.ibkr.model.*
import com.coradec.coradeck.bus.model.impl.BasicBusNode
import com.coradec.coradeck.core.model.Priority.B1
import com.coradec.coradeck.core.model.Priority.B3
import com.coradec.coradeck.core.util.here
import com.coradec.coradeck.ctrl.module.CoraControl
import com.coradec.coradeck.text.model.LocalText
import com.ib.client.*
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

object InteractiveResponseHandler : BasicBusNode(), EWrapper {
    private val IMMEX = CoraControl.IMMEX
    private val TEXT_TICK_PRICE = LocalText("TickPriceInfo")
    private val TEXT_TICK_SIZE = LocalText("TickSizeInfo")
    private val TEXT_TICK_OPTION_COMPUTATION = LocalText("TickOptionComputation")
    private val TEXT_TICK_GENERIC = LocalText("TickGeneric")
    private val TEXT_TICK_STRING = LocalText("TickString")
    private val TEXT_TICK_EFP = LocalText("TickEFP")
    private val TEXT_ORDER_STATUS = LocalText("OrderStatus")
    private val TEXT_OPEN_ORDER = LocalText("OpenOrder")
    private val TEXT_END_OPEN_ORDERS = LocalText("EndOpenOrders")
    private val TEXT_UPDATE_ACCOUNT_VALUE = LocalText("UpdateAccountValue")
    private val TEXT_UPDATE_ACCOUNT_VALUE_CURRENCY = LocalText("UpdateAccountValueCurrency")
    private val TEXT_UPDATE_PORTFOLIO = LocalText("UpdatePortfolio")
    private val TEXT_ACCOUNT_LAST_UPDATED = LocalText("AccountLastUpdated")
    private val TEXT_ACCOUNT_UPDATED = LocalText("AccountUpdated")
    private val TEXT_NEXT_ID = LocalText("NextOrderId")
    private val TEXT_CONTRACT_DETAILS = LocalText("ContractDetails")
    private val TEXT_BOND_CONTRACT_DETAILS = LocalText("BondContractDetails")
    private val TEXT_CONTRACT_DETAILS_END = LocalText("EndContractDetails")
    private val TEXT_EXECUTION_DETAILS = LocalText("ExecutionDetails")
    private val TEXT_EXECUTION_DETAILS_END = LocalText("EndExecutionDetails")
    private val TEXT_UPDATE_MARKET_DEPTH = LocalText("UpdateMarketDepth")
    private val TEXT_UPDATE_MARKET_DEPTH_L2 = LocalText("UpdateMarketDepthL2")
    private val TEXT_UPDATE_MARKET_DEPTH_L2_SMART = LocalText("UpdateMarketDepthL2Smart")
    private val TEXT_NEWS_BULLETIN_UPDATE = LocalText("NewsBulletinUpdate")
    private val TEXT_MANAGED_ACCOUNTS = LocalText("ManagedAccounts")
    private val TEXT_FA_CONFIG = LocalText("FinAdvConfiguration")
    private val TEXT_HISTORICAL_DATA_BAR = LocalText("HistoricalDataBar")
    private val TEXT_SCANNER_PARAMETERS = LocalText("ScannerParameters")
    private val TEXT_SCANNER_DATA = LocalText("ScannerData")
    private val TEXT_END_SCANNER_DATA = LocalText("EndScannerData")
    private val TEXT_REALTIME_DATA = LocalText("RealtimeData")
    private val TEXT_CURRENT_TIME = LocalText("CurrentTime")
    private val TEXT_FUNDAMENTAL_DATA = LocalText("FundamentalData")
    private val TEXT_DELTA_NEUTRAL_CONTRACT = LocalText("DeltaNeutralContract")
    private val TEXT_END_SNAPSHOT_TICK = LocalText("EndSnapshotTick")
    private val TEXT_COMMISSION_REPORT = LocalText("CommissionReport")
    private val TEXT_POSITION = LocalText("Position")
    private val TEXT_END_POSITIONS = LocalText("EndPositions")
    private val TEXT_ACCOUNT_SUMMARY = LocalText("AccountSummary")
    private val TEXT_ACCOUNT_SUMMARY_CURRENCY = LocalText("AccountSummary")
    private val TEXT_END_ACCOUNT_SUMMARY = LocalText("EndAccountSummary")
    private val TEXT_VERIFY_MESSAGE_API = LocalText("VerifyMessageAPI")
    private val TEXT_VERIFICATION_SUCCESSFUL = LocalText("VerificationSuccessful")
    private val TEXT_VERIFICATION_FAILED = LocalText("VerificationFailed")
    private val TEXT_VERIFY_AND_AUTH_MESSAGE_API = LocalText("VerifyAndAuthMessageAPI")
    private val TEXT_VERIFICATION_AND_AUTH_SUCCESSFUL = LocalText("VerificationAndAuthSuccessful")
    private val TEXT_VERIFICATION_AND_AUTH_FAILED = LocalText("VerificationAndAuthFailed")
    private val TEXT_DISPLAY_GROUP_LIST = LocalText("DisplayGroupList")
    private val TEXT_DISPLAY_GROUP_UPDATED = LocalText("DisplayGroupUpdated")
    private val TEXT_ERROR = LocalText("Error")
    private val TEXT_CODED_ERROR = LocalText("CodedError")
    private val TEXT_CODED_NOTIFICATION = LocalText("CodedNotification")
    private val TEXT_CONNECTION_CLOSED = LocalText("ConnectionClosed")
    private val TEXT_ACKNOWLEDGE_CONNECT = LocalText("AcknowledgeConnect")
    private val TEXT_POSITION_MULTI = LocalText("PositionMulti")
    private val TEXT_END_POSITIONS_MULTI = LocalText("EndPositionsMulti")
    private val TEXT_UPDATE_ACCOUNT = LocalText("UpdateAccount")
    private val TEXT_UPDATE_ACCOUNT_CURRENCY = LocalText("UpdateAccountCurrency")
    private val TEXT_ACCOUNT_UPDATED_MULTI = LocalText("AccountUpdatedMulti")
    private val TEXT_SEC_DEF_OPTION_PARA = LocalText("SecurityDefinitionOptionParameter")
    private val TEXT_END_SEC_DEF_OPTION_PARA = LocalText("SecurityDefinitionOptionParameterEnd")
    private val TEXT_SOFT_DOLLAR_TIERS = LocalText("SoftDollarTiers")
    private val TEXT_FAMILY_CODES = LocalText("FamilyCodes")
    private val TEXT_SYMBOL_SAMPLES = LocalText("SymbolSamples")
    private val TEXT_HISTORICAL_DATA_END = LocalText("EndHistoricalData")
    private val TEXT_MARKET_DEPTH_EXCHANGES = LocalText("MarketDepthExchanges")
    private val TEXT_NEWS_TICK = LocalText("NewsTick")
    private val TEXT_SMART_COMPONENTS = LocalText("SmartComponents")
    private val TEXT_TICK_REQUEST_PARAMETERS = LocalText("TickRequestParameters")
    private val TEXT_NEWS_PROVIDERS = LocalText("NewsProviders")
    private val TEXT_NEWS_ARTICLE = LocalText("NewsArticle")
    private val TEXT_HISTORICAL_NEWS = LocalText("HistoricalNews")
    private val TEXT_HISTORICAL_NEWS_END = LocalText("HistoricalNewsEnd")
    private val TEXT_HISTORICAL_NEWS_END_BUT_MORE = LocalText("HistoricalNewsEndButMore")
    private val TEXT_HEAD_TIMESTAMP = LocalText("HeadTimestamp")
    private val TEXT_HISTOGRAM_DATA = LocalText("HistogramData")
    private val TEXT_HISTORICAL_DATA_UPDATE = LocalText("HistoricalDataUpdate")
    private val TEXT_MARKET_DATA_REROUTE = LocalText("MarketDataReroute")
    private val TEXT_MARKET_DEPTH_REROUTE = LocalText("MarketDepthReroute")
    private val TEXT_MARKET_RULE = LocalText("MarketRule")
    private val TEXT_PROFIT_AND_LOSS = LocalText("Profit&Loss")
    private val TEXT_PROFIT_AND_LOSS_SINGLE = LocalText("SingleProfit&Loss")
    private val TEXT_HISTORICAL_TICKS = LocalText("HistoricalTicks")
    private val TEXT_HISTORICAL_TICKS_END = LocalText("HistoricalTicksEnd")
    private val TEXT_HISTORICAL_BID_ASK_TICKS = LocalText("HistoricalBidAskTicks")
    private val TEXT_HISTORICAL_BID_ASK_TICKS_END = LocalText("HistoricalBidAskTicksEnd")
    private val TEXT_HISTORICAL_LAST_TICKS = LocalText("HistoricalLastTicks")
    private val TEXT_HISTORICAL_LAST_TICKS_END = LocalText("HistoricalLastTicksEnd")
    private val TEXT_TICK_BY_TICK_ALL_LAST = LocalText("TickByTickAllLast")
    private val TEXT_TICK_BY_TICK_BID_ASK = LocalText("TickByTickBidAsk")
    private val TEXT_TICK_BY_TICK_MID_POINT = LocalText("TickByTickMidPoint")
    private val TEXT_ORDER_BOUND = LocalText("OrderBound")
    private val TEXT_ORDER_COMPLETED = LocalText("OrderComplete")
    private val TEXT_ORDERS_COMPLETED_END = LocalText("AllOrdersComplete")
    private val TEXT_FINANCIAL_ADVISOR_REPLACED = LocalText("FAReplaced")
    private val TEXT_WSH_METADATA = LocalText("WSHMetaData")
    private val TEXT_WSH_EVENT_DATA = LocalText("WSHEventData")

    private lateinit var currentAccount: String

    override fun onInitializing() {
        super.onInitializing()
        currentAccount = accept(GetCurrentAccountVoucher(here)).content.value
    }

    override fun tickPrice(tickerId: Int, field: Int, price: Double, attributes: TickAttrib) {
        info(TEXT_TICK_PRICE, tickerId, field, TickType.getField(field), price, attributes.repr)
        IMMEX.inject(PriceTickEvent(here, tickerId, field, price, attributes))
    }

    override fun tickSize(tickerId: Int, field: Int, size: Decimal) {
        info(TEXT_TICK_SIZE, tickerId, field, TickType.getField(field), size.longValue())
        IMMEX.inject(SizeTickEvent(here, tickerId, field, size.longValue()))
    }

    override fun tickOptionComputation(
        tickerId: Int, field: Int, tickAttrib: Int, impliedVolatility: Double, delta: Double, optionPrice: Double,
        pvDividend: Double, gamma: Double, vega: Double, theta: Double, underPrice: Double
    ) {
        val tickBase = TickBase(tickAttrib)
        info(
            TEXT_TICK_OPTION_COMPUTATION, tickerId, field, TickType.getField(field), tickBase, impliedVolatility, delta,
            optionPrice, pvDividend, gamma, vega, theta, underPrice
        )
        IMMEX.inject(
            OptionComputationTickEvent(
                here, tickerId, field, tickBase, impliedVolatility, delta, optionPrice, pvDividend, gamma, vega, theta, underPrice
            )
        )
    }

    override fun tickGeneric(tickerId: Int, field: Int, value: Double) {
        info(TEXT_TICK_GENERIC, tickerId, field, TickType.getField(field), value)
        IMMEX.inject(GenericTickEvent(here, tickerId, field, value))
    }

    override fun tickString(tickerId: Int, field: Int, value: String) {
        info(TEXT_TICK_STRING, tickerId, field, TickType.getField(field), value)
        IMMEX.inject(StringTickEvent(here, tickerId, field, value))
    }

    override fun tickEFP(
        tickerId: Int, field: Int, basisPoints: Double, formattedBasisPoints: String, impliedFuture: Double, holdDays: Int,
        futureLastTradeDate: String, dividendImpact: Double, dividendsToLastTradeDate: Double
    ) {
        info(
            TEXT_TICK_EFP, tickerId, field, TickType.getField(field), basisPoints, formattedBasisPoints,
            impliedFuture, holdDays, futureLastTradeDate, dividendImpact, dividendsToLastTradeDate
        )
        IMMEX.inject(
            ExchangeForPhysicalsTickEvent(
                here, tickerId, field, basisPoints, formattedBasisPoints, impliedFuture, holdDays, futureLastTradeDate,
                dividendImpact, dividendsToLastTradeDate
            )
        )
    }

    override fun orderStatus(
        orderId: Int, status: String, filled: Decimal, remaining: Decimal, averageFillPrice: Double, permId: Int, parentId: Int,
        lastFillPrice: Double, clientId: Int, holdingReason: String, marketCapPrice: Double
    ) {
        val orderStatus = OrderStatus.get(status)
        info(
            TEXT_ORDER_STATUS, orderId, orderStatus, filled, remaining, averageFillPrice, lastFillPrice, permId, parentId, clientId,
            holdingReason, marketCapPrice
        )
        IMMEX.inject(
            OrderStatusUpdateEvent(
                here, orderId, orderStatus, filled.longValue(), remaining.longValue(), averageFillPrice, permId, parentId,
                lastFillPrice, clientId, holdingReason, marketCapPrice
            )
        )
    }

    override fun openOrder(orderId: Int, contract: Contract, order: Order, orderState: OrderState) {
        info(TEXT_OPEN_ORDER, orderId, contract, order, orderState)
        IMMEX.inject(OpenOrderEvent(here, orderId, contract, order, orderState))
    }

    override fun openOrderEnd() {
        info(TEXT_END_OPEN_ORDERS)
        IMMEX.inject(EndOrdersEvent(here))
    }

    override fun updateAccountValue(key: String, value: String, currency: String?, accountName: String) {
        if (currency == null) info(TEXT_UPDATE_ACCOUNT_VALUE, accountName, key, value)
        else info(TEXT_UPDATE_ACCOUNT_VALUE_CURRENCY, accountName, key, currency, value)
        IMMEX.inject(UpdateAccountValueEvent(here, accountName, key, value, currency))
    }

    override fun updatePortfolio(
        contract: Contract, position: Decimal, marketPrice: Double, marketValue: Double, averageCost: Double, unrealizedPNL: Double,
        realizedPNL: Double, accountName: String
    ) {
        info(
            TEXT_UPDATE_PORTFOLIO, accountName, contract, position, marketPrice, marketValue, averageCost,
            unrealizedPNL, realizedPNL
        )
        IMMEX.inject(
            UpdatePortfolioEvent(
                here, accountName, contract, position.longValue(), marketPrice, marketValue, averageCost, unrealizedPNL, realizedPNL
            )
        )
    }

    override fun updateAccountTime(timestamp: String) {
        info(TEXT_ACCOUNT_LAST_UPDATED, timestamp)
        IMMEX.inject(AccountUpdateTimeEvent(here, currentAccount, timestamp))
    }

    override fun accountDownloadEnd(accountName: String) {
        info(TEXT_ACCOUNT_UPDATED, accountName)
        IMMEX.inject(AccountUpdatedEvent(here, accountName))
    }

    override fun nextValidId(orderId: Int) {
        info(TEXT_NEXT_ID, orderId)
        IMMEX.inject(NextOrderIdEvent(here, orderId))
    }

    override fun contractDetails(requestId: Int, contractDetails: ContractDetails) {
        info(TEXT_CONTRACT_DETAILS, requestId, contractDetails)
        IMMEX.inject(ContractDetailsEvent(here, requestId, contractDetails))
    }

    override fun bondContractDetails(requestId: Int, contractDetails: ContractDetails) {
        info(TEXT_BOND_CONTRACT_DETAILS, requestId, contractDetails)
        IMMEX.inject(BondContractDetailsEvent(here, requestId, contractDetails))
    }

    override fun contractDetailsEnd(requestId: Int) {
        info(TEXT_CONTRACT_DETAILS_END, requestId)
        IMMEX.inject(EndContractDetailsEvent(here, requestId))
    }

    override fun execDetails(requestId: Int, contract: Contract, execution: Execution) {
        info(TEXT_EXECUTION_DETAILS, requestId, contract, execution)
        IMMEX.inject(ExecutionDetailsEvent(here, requestId, contract, execution))
    }

    override fun execDetailsEnd(requestId: Int) {
        info(TEXT_EXECUTION_DETAILS_END, requestId)
        IMMEX.inject(EndExecutionDetailsEvent(here, requestId))
    }

    override fun updateMktDepth(tickerId: Int, position: Int, operation: Int, side: Int, price: Double, size: Decimal) {
        val op = MarketDepthOperation(operation)
        val bidask = DemandSide(side)
        info(TEXT_UPDATE_MARKET_DEPTH, tickerId, position, op, price, size.longValue(), bidask)
        IMMEX.inject(UpdateMarketDepthEvent(here, tickerId, position, op, bidask, price, size.longValue()))
    }

    override fun updateMktDepthL2(
        tickerId: Int, position: Int, marketMaker: String, operation: Int, side: Int, price: Double, size: Decimal,
        smart: Boolean
    ) {
        val op = MarketDepthOperation(operation)
        val bidask = DemandSide(side)
        if (smart) info(TEXT_UPDATE_MARKET_DEPTH_L2, tickerId, position, marketMaker, op, price, size.longValue(), bidask)
        else info(TEXT_UPDATE_MARKET_DEPTH_L2_SMART, tickerId, position, marketMaker, op, price, size.longValue(), bidask)
        IMMEX.inject(
            UpdateMarketDepthLevel2Event(
                here,
                tickerId,
                position,
                op,
                bidask,
                price,
                size.longValue(),
                marketMaker,
                smart
            )
        )
    }

    override fun updateNewsBulletin(msgId: Int, msgType: Int, message: String, origExchange: String) {
        val type = NewsBulletinState(msgType)
        info(TEXT_NEWS_BULLETIN_UPDATE, msgId, message, type, origExchange)
        IMMEX.inject(NewsBulletinUpdateEvent(here, msgId, type, message, origExchange))
    }

    override fun managedAccounts(accounts: String) {
        val accountList = accounts.split(',').map { it.trim() }
        info(TEXT_MANAGED_ACCOUNTS, accountList.joinToString(", "))
        IMMEX.inject(ManagedAccountsEvent(here, accountList))
    }

    override fun receiveFA(faDataType: Int, faXmlData: String) {
        val type = FinancialAdvisorDataType(faDataType)
        info(TEXT_FA_CONFIG, faXmlData, type)
        IMMEX.inject(FinancialAdvisorConfigurationEvent(here, type, faXmlData))
    }

    override fun historicalData(requestId: Int, bar: Bar) {
        info(TEXT_HISTORICAL_DATA_BAR, requestId, bar)
        IMMEX.inject(HistoricalDataEvent(here, requestId, bar))
    }

    override fun scannerParameters(parameters: String) {
        info(TEXT_SCANNER_PARAMETERS, parameters)
        IMMEX.inject(ScannerParameterEvent(here, parameters))
    }

    override fun scannerData(
        requestId: Int, rank: Int, contractDetails: ContractDetails, distance: String, benchmark: String, projection: String,
        comboLegs: String
    ) {
        info(TEXT_SCANNER_DATA, requestId, rank, contractDetails, distance, benchmark, projection, comboLegs)
        IMMEX.inject(ScannerDataEvent(here, requestId, rank, contractDetails, distance, benchmark, projection, comboLegs))
    }

    override fun scannerDataEnd(requestId: Int) {
        info(TEXT_END_SCANNER_DATA, requestId)
        IMMEX.inject(EndScannerDataEvent(here, requestId))
    }

    override fun realtimeBar(
        requestId: Int, datetime: Long, open: Double, high: Double, low: Double, close: Double, volume: Decimal, wap: Decimal,
        count: Int
    ) {
        val timestamp = LocalDateTime.ofInstant(Instant.ofEpochMilli(datetime), ZoneId.systemDefault())
        info(TEXT_REALTIME_DATA, requestId, timestamp, open, high, low, close, volume.longValue(), wap.value(), count)
        IMMEX.inject(RealtimeBarEvent(here, requestId, timestamp, open, high, low, close, volume.longValue(), wap.value(), count))
    }

    override fun currentTime(datetime: Long) {
        val timestamp = LocalDateTime.ofInstant(Instant.ofEpochMilli(datetime), ZoneId.systemDefault())
        info(TEXT_CURRENT_TIME, timestamp)
        IMMEX.inject(TWSCurrentTimeEvent(here, timestamp))
    }

    override fun fundamentalData(requestId: Int, xmlData: String) {
        info(TEXT_FUNDAMENTAL_DATA, requestId, xmlData)
        IMMEX.inject(FundamentalDataEvent(here, requestId, xmlData))
    }

    override fun deltaNeutralValidation(requestId: Int, deltaNeutralContract: DeltaNeutralContract) {
        info(TEXT_DELTA_NEUTRAL_CONTRACT, requestId, deltaNeutralContract)
        IMMEX.inject(DeltaNeutralContractEvent(here, requestId, deltaNeutralContract))
    }

    override fun tickSnapshotEnd(tickerId: Int) {
        info(TEXT_END_SNAPSHOT_TICK, tickerId)
        IMMEX.inject(EndSnapshotTickEvent(here, tickerId))
    }

    override fun marketDataType(tickerId: Int, marketDataType: Int) {
        val mdtype = InteractiveMarketDataType(marketDataType)
        info(TEXT_END_SNAPSHOT_TICK, tickerId, mdtype)
        IMMEX.inject(MarketDataTypeEvent(here, tickerId))
    }

    override fun commissionReport(commissionReport: CommissionReport) {
        info(TEXT_COMMISSION_REPORT, commissionReport)
        IMMEX.inject(CommissionReportEvent(here, commissionReport))
    }

    override fun position(account: String, contract: Contract, position: Decimal, avgCost: Double) {
        info(TEXT_POSITION, account, position.longValue(), contract, avgCost)
        IMMEX.inject(PositionEvent(here, account, contract, position.longValue(), avgCost))
    }

    override fun positionEnd() {
        info(TEXT_END_POSITIONS)
        IMMEX.inject(EndPositionEvent(here))
    }

    override fun accountSummary(requestId: Int, account: String, key: String, value: String, currency: String?) {
        if (currency == null) info(TEXT_ACCOUNT_SUMMARY, requestId, account, key, value)
        else info(TEXT_ACCOUNT_SUMMARY_CURRENCY, requestId, account, key, value, currency)
        IMMEX.inject(AccountSummaryPositionEvent(here, requestId, account, key, value, currency, priority = B1))
    }

    override fun accountSummaryEnd(requestId: Int) {
        info(TEXT_END_ACCOUNT_SUMMARY, requestId)
        IMMEX.inject(EndAccountSummaryEvent(here, requestId, priority = B3))
    }

    override fun verifyMessageAPI(apiData: String) {
        info(TEXT_VERIFY_MESSAGE_API, apiData)
        IMMEX.inject(MessageAPIVerificationEvent(here, apiData))
    }

    override fun verifyCompleted(successful: Boolean, errorText: String?) {
        if (successful) {
            info(TEXT_VERIFICATION_SUCCESSFUL)
            IMMEX.inject(VerificationSuccessfulEvent(here))
        } else {
            val errmsg = errorText ?: "Unknown problem!"
            info(TEXT_VERIFICATION_FAILED, errmsg)
            IMMEX.inject(VerificationFailedEvent(here, errmsg))
        }
    }

    override fun verifyAndAuthMessageAPI(apiData: String, xyzChallenge: String) {
        info(TEXT_VERIFY_AND_AUTH_MESSAGE_API, apiData, xyzChallenge)
        IMMEX.inject(VerificationAndAuthMessageAPIEvent(here, apiData, xyzChallenge))
    }

    override fun verifyAndAuthCompleted(successful: Boolean, errorText: String?) {
        if (successful) {
            info(TEXT_VERIFICATION_AND_AUTH_SUCCESSFUL)
            IMMEX.inject(VerificationSuccessfulEvent(here))
        } else {
            val errmsg = errorText ?: "Unknown problem!"
            info(TEXT_VERIFICATION_AND_AUTH_FAILED, errmsg)
            IMMEX.inject(VerificationFailedEvent(here, errmsg))
        }
    }

    override fun displayGroupList(requestId: Int, groups: String) {
        info(TEXT_DISPLAY_GROUP_LIST, requestId, groups)
        IMMEX.inject(DisplayGroupsEvent(here, requestId, groups))
    }

    override fun displayGroupUpdated(requestId: Int, contractInfo: String) {
        info(TEXT_DISPLAY_GROUP_UPDATED, requestId, contractInfo)
        IMMEX.inject(DisplayGroupUpdateEvent(here, requestId, contractInfo))
    }

    override fun error(exception: Exception) {
        error(exception)
        IMMEX.inject(ErrorEvent(here, problem = exception))
    }

    override fun error(errorMessage: String) {
        error(TEXT_ERROR, errorMessage)
        IMMEX.inject(ErrorEvent(here, message = errorMessage))
    }

    override fun error(requestId: Int, errorCode: Int, errorMessage: String) {
        if (requestId == -1) {
            info(TEXT_CODED_NOTIFICATION, errorCode, errorMessage)
            IMMEX.inject(GeneralNotification(here, errorCode, errorMessage))
        } else {
            error(TEXT_CODED_ERROR, requestId, errorCode, errorMessage)
            IMMEX.inject(CodedErrorEvent(here, requestId = requestId, errorCode = errorCode, message = errorMessage))
        }
    }

    override fun connectionClosed() {
        info(TEXT_CONNECTION_CLOSED)
        IMMEX.inject(ConnectionClosedEvent(here))
    }

    override fun connectAck() {
        info(TEXT_ACKNOWLEDGE_CONNECT)
        IMMEX.inject(ConnectAcknowledgedEvent(here))
    }

    override fun positionMulti(requestId: Int, account: String, modelCode: String, contract: Contract, pos: Decimal, avgCost: Double) {
        info(TEXT_POSITION_MULTI, requestId, account, modelCode, contract, pos.longValue(), avgCost)
        IMMEX.inject(MultiPositionEvent(here, requestId, account, contract, pos.longValue(), avgCost, modelCode))
    }

    override fun positionMultiEnd(requestId: Int) {
        info(TEXT_END_POSITIONS_MULTI, requestId)
        IMMEX.inject(EndMultiPositionEvent(here, requestId))
    }

    override fun accountUpdateMulti(
        requestId: Int, account: String, modelCode: String, key: String, value: String, currency: String?
    ) {
        if (currency == null) info(TEXT_UPDATE_ACCOUNT, requestId, account, key, value)
        else info(TEXT_UPDATE_ACCOUNT_CURRENCY, requestId, account, key, currency, value)
        IMMEX.inject(AccountUpdateEvent(here, requestId, account, modelCode, key, value, currency))
    }

    override fun accountUpdateMultiEnd(requestId: Int) {
        info(TEXT_ACCOUNT_UPDATED_MULTI, requestId)
        IMMEX.inject(AccountUpdatedEndEvent(here, requestId))
    }

    override fun securityDefinitionOptionalParameter(
        requestId: Int, exchange: String, underlyingId: Int, tradingClass: String, multiplier: String, expirations: Set<String>,
        strikes: Set<Double>
    ) {
        info(TEXT_SEC_DEF_OPTION_PARA, requestId, exchange, underlyingId, tradingClass, multiplier, expirations, strikes)
        IMMEX.inject(
            SecurityDefinitionOptionParameterEvent(
            here, requestId, exchange, underlyingId, tradingClass, multiplier, expirations, strikes
        )
        )
    }

    override fun securityDefinitionOptionalParameterEnd(requestId: Int) {
        info(TEXT_END_SEC_DEF_OPTION_PARA, requestId)
        IMMEX.inject(SecurityDefinitionOptionParameterEndEvent(here, requestId))
    }

    override fun softDollarTiers(requestId: Int, tiers: Array<out SoftDollarTier>) {
        val tierList = tiers.toList()
        info(TEXT_SOFT_DOLLAR_TIERS, requestId, tierList)
        IMMEX.inject(SoftDollarTiersEvent(here, requestId, tierList))
    }

    override fun familyCodes(familyCodes: Array<out FamilyCode>) {
        val codeList = familyCodes.toList()
        info(TEXT_FAMILY_CODES, codeList)
        IMMEX.inject(FamilyCodesEvent(here, codeList))
    }

    override fun symbolSamples(requestId: Int, contractDescriptions: Array<out ContractDescription>) {
        val contractDescrList = contractDescriptions.toList()
        info(TEXT_SYMBOL_SAMPLES, contractDescrList)
        IMMEX.inject(SymbolSamplesEvent(here, contractDescrList))
    }

    override fun historicalDataEnd(requestId: Int, start: String, end: String) {
        info(TEXT_HISTORICAL_DATA_END, requestId, start, end)
        IMMEX.inject(HistoricalDataEndEvent(here, requestId, start, end))
    }

    override fun mktDepthExchanges(depthMarketDataDescrs: Array<out DepthMktDataDescription>) {
        val descrList = depthMarketDataDescrs.toList()
        info(TEXT_MARKET_DEPTH_EXCHANGES, descrList)
        IMMEX.inject(MarketDepthExchangesEvent(here, descrList))
    }

    override fun tickNews(tickerId: Int, datetime: Long, provider: String, articleId: String, headline: String, extraData: String) {
        val timestamp = LocalDateTime.ofInstant(Instant.ofEpochMilli(datetime), ZoneId.systemDefault())
        info(TEXT_NEWS_TICK, tickerId, timestamp, provider, articleId, headline, extraData)
        IMMEX.inject(NewsTickEvent(here, tickerId, timestamp, provider, articleId, headline, extraData))
    }

    override fun smartComponents(requestId: Int, smartComponents: Map<Int, Map.Entry<String, Char>>) {
        info(TEXT_SMART_COMPONENTS, requestId, smartComponents)
        IMMEX.inject(SmartComponentsEvent(here, requestId, smartComponents))
    }

    override fun tickReqParams(tickerId: Int, minTick: Double, bboxchg: String, snapshotPermissions: Int) {
        info(TEXT_TICK_REQUEST_PARAMETERS, tickerId, minTick, bboxchg, snapshotPermissions)
        IMMEX.inject(TickRequestParametersEvent(here, tickerId, minTick, bboxchg, snapshotPermissions))
    }

    override fun newsProviders(newsProviders: Array<out NewsProvider>) {
        val providers = newsProviders.toList()
        info(TEXT_NEWS_PROVIDERS, providers)
        IMMEX.inject(NewsProvidersEvent(here, providers))
    }

    override fun newsArticle(requestId: Int, articleType: Int, articleText: String) {
        val type = ArticleType(articleType)
        info(TEXT_NEWS_ARTICLE, requestId, type, if (type == ArticleType.PDF) "<PDF>" else articleText)
        IMMEX.inject(NewsArticleEvent(here, requestId, type, articleText))
    }

    override fun historicalNews(requestId: Int, time: String, providerCode: String, articleId: String, headline: String) {
        info(TEXT_HISTORICAL_NEWS, requestId, articleId, time, providerCode, headline)
        IMMEX.inject(HistoricalNewsEvent(here, requestId, time, providerCode, articleId, headline))
    }

    override fun historicalNewsEnd(requestId: Int, hasMore: Boolean) {
        if (hasMore) info(TEXT_HISTORICAL_NEWS_END_BUT_MORE, requestId)
        else info(TEXT_HISTORICAL_NEWS_END, requestId)
        IMMEX.inject(HistoricalNewsEndEvent(here, requestId, hasMore))
    }

    override fun headTimestamp(requestId: Int, headTimestamp: String) {
        info(TEXT_HEAD_TIMESTAMP, requestId, headTimestamp)
        IMMEX.inject(HeadTimestampEvent(here, requestId, headTimestamp))
    }

    override fun histogramData(requestId: Int, data: List<HistogramEntry>) {
        info(TEXT_HISTOGRAM_DATA, requestId, data)
        IMMEX.inject(HistogramDataEvent(here, requestId, data))
    }

    override fun historicalDataUpdate(requestId: Int, bar: Bar) {
        info(TEXT_HISTORICAL_DATA_UPDATE, requestId, bar)
        IMMEX.inject(HistoricalDataUpdateEvent(here, requestId, bar))
    }

    override fun rerouteMktDataReq(requestId: Int, contractId: Int, exchange: String) {
        info(TEXT_MARKET_DATA_REROUTE, requestId, contractId, exchange)
        IMMEX.inject(MarketDataReroutingEvent(here, requestId, contractId, exchange))
    }

    override fun rerouteMktDepthReq(requestId: Int, contractId: Int, exchange: String) {
        info(TEXT_MARKET_DEPTH_REROUTE, requestId, contractId, exchange)
        IMMEX.inject(MarketDepthReroutingEvent(here, requestId, contractId, exchange))
    }

    override fun marketRule(ruleId: Int, priceIncrements: Array<out PriceIncrement>) {
        val incs = priceIncrements.toList()
        info(TEXT_MARKET_RULE, ruleId, incs)
        IMMEX.inject(MarketRuleEvent(here, ruleId, incs))
    }

    override fun pnl(requestId: Int, dailyPnL: Double, unrealizedPnL: Double, realizedPnL: Double) {
        info(TEXT_PROFIT_AND_LOSS, requestId, dailyPnL, unrealizedPnL, realizedPnL)
        IMMEX.inject(ProfitAndLossEvent(here, requestId, dailyPnL, unrealizedPnL, realizedPnL))
    }

    override fun pnlSingle(
        requestId: Int, position: Decimal, dailyPnL: Double, unrealizedPnL: Double, realizedPnL: Double, value: Double
    ) {
        info(TEXT_PROFIT_AND_LOSS_SINGLE, requestId, position.longValue(), dailyPnL, unrealizedPnL, realizedPnL, value)
        IMMEX.inject(SingleProfitAndLossEvent(here, requestId, position.longValue(), dailyPnL, unrealizedPnL, realizedPnL, value))
    }

    override fun historicalTicks(requestId: Int, ticks: List<HistoricalTick>, done: Boolean) {
        info(TEXT_HISTORICAL_TICKS, requestId, ticks)
        if (done) info(TEXT_HISTORICAL_TICKS_END, requestId)
        IMMEX.inject(HistoricalTicksEvent(here, requestId, ticks, done))
    }

    override fun historicalTicksBidAsk(requestId: Int, ticks: List<HistoricalTickBidAsk>, done: Boolean) {
        info(TEXT_HISTORICAL_BID_ASK_TICKS, requestId, ticks)
        if (done) info(TEXT_HISTORICAL_BID_ASK_TICKS_END, requestId)
        IMMEX.inject(HistoricalBidAskTicksEvent(here, requestId, ticks, done))
    }

    override fun historicalTicksLast(requestId: Int, ticks: List<HistoricalTickLast>, done: Boolean) {
        info(TEXT_HISTORICAL_LAST_TICKS, requestId, ticks)
        if (done) info(TEXT_HISTORICAL_LAST_TICKS_END, requestId)
        IMMEX.inject(HistoricalLastTicksEvent(here, requestId, ticks, done))
    }

    override fun tickByTickAllLast(
        requestId: Int, tickType: Int, datetime: Long, price: Double, size: Decimal, tickAttrib: TickAttribLast,
        exchange: String, specConds: String
    ) {
        val timestamp = LocalDateTime.ofInstant(Instant.ofEpochMilli(datetime), ZoneId.systemDefault())
        val sz = size.longValue()
        val type = TickType.get(tickType)
        info(TEXT_TICK_BY_TICK_ALL_LAST, requestId, type, exchange, timestamp, sz, price, tickAttrib, specConds)
        IMMEX.inject(TickByTickAllLastEvent(here, requestId, type, timestamp, price, sz, tickAttrib, exchange, specConds))
    }

    override fun tickByTickBidAsk(
        requestId: Int, datetime: Long, bidPrice: Double, askPrice: Double, bidSize: Decimal, askSize: Decimal,
        tickAttrib: TickAttribBidAsk
    ) {
        val timestamp = LocalDateTime.ofInstant(Instant.ofEpochMilli(datetime), ZoneId.systemDefault())
        val bidsz = bidSize.longValue()
        val asksz = askSize.longValue()
        info(TEXT_TICK_BY_TICK_BID_ASK, requestId, timestamp, bidSize, bidPrice, askSize, askPrice, tickAttrib)
        IMMEX.inject(TickByTickBidAskEvent(here, requestId, timestamp, bidPrice, askPrice, bidsz, asksz, tickAttrib))
    }

    override fun tickByTickMidPoint(requestId: Int, datetime: Long, price: Double) {
        val timestamp = LocalDateTime.ofInstant(Instant.ofEpochMilli(datetime), ZoneId.systemDefault())
        info(TEXT_TICK_BY_TICK_MID_POINT, requestId, timestamp, price)
        IMMEX.inject(TickByTickBidMidPointEvent(here, requestId, timestamp, price))
    }

    override fun orderBound(orderId: Long, apiClientId: Int, apiOrderId: Int) {
        info(TEXT_ORDER_BOUND, apiClientId, apiOrderId, orderId)
        IMMEX.inject(OrderBoundEvent(here, orderId, apiClientId, apiOrderId))
    }

    override fun completedOrder(contract: Contract, order: Order, state: OrderState) {
        info(TEXT_ORDER_COMPLETED, contract, order, state)
        IMMEX.inject(OrderCompletedEvent(here, contract, order, state))
    }

    override fun completedOrdersEnd() {
        info(TEXT_ORDERS_COMPLETED_END)
        IMMEX.inject(AllOrdersCompleteEvent(here))
    }

    override fun replaceFAEnd(requestId: Int, message: String) {
        info(TEXT_FINANCIAL_ADVISOR_REPLACED, requestId, message)
        IMMEX.inject(FinancialAdvisorReplacedEvent(here, requestId, message))
    }

    override fun wshMetaData(requestId: Int, metaDataJSON: String) {
        info(TEXT_WSH_METADATA, requestId, metaDataJSON)
        IMMEX.inject(WSHMetaDataEvent(here, requestId, metaDataJSON))
    }

    override fun wshEventData(requestId: Int, eventDataJSON: String) {
        info(TEXT_WSH_EVENT_DATA, requestId, eventDataJSON)
        IMMEX.inject(WSHEventDataEvent(here, requestId, eventDataJSON))
    }

    private val TickAttrib?.repr: String get() = this?.toString()?.trim() ?: "none"
}
