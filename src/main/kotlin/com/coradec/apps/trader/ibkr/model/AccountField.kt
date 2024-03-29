package com.coradec.apps.trader.ibkr.model

import com.coradec.apps.trader.ibkr.trouble.AccountFieldNotFoundException
import com.coradec.apps.trader.model.MoneyAmount
import kotlin.reflect.KClass

enum class AccountField(val type: KClass<*>) {
    AccountCode(String::class),
    AccountOrGroup(String::class),
    AccountReady(Boolean::class),
    AccountType(String::class),
    AccruedCash(MoneyAmount::class),
    `AccruedCash-C`(MoneyAmount::class),
    `AccruedCash-S`(MoneyAmount::class),
    AccruedDividend(MoneyAmount::class),
    `AccruedDividend-C`(MoneyAmount::class),
    `AccruedDividend-S`(MoneyAmount::class),
    AvailableFunds(MoneyAmount::class),
    `AvailableFunds-C`(MoneyAmount::class),
    `AvailableFunds-S`(MoneyAmount::class),
    Billable(MoneyAmount::class),
    `Billable-C`(MoneyAmount::class),
    `Billable-S`(MoneyAmount::class),
    BuyingPower(MoneyAmount::class),
    CashBalance(MoneyAmount::class),
    ColumnPrio(MoneyAmount::class),
    `ColumnPrio-S`(MoneyAmount::class),
    `ColumnPrio-C`(MoneyAmount::class),
    CorporateBondValue(MoneyAmount::class),
    Currency(java.util.Currency::class),
    Cushion(MoneyAmount::class),
    DayTradesRemaining(Int::class),
    `DayTradesRemainingT+1`(Int::class),
    `DayTradesRemainingT+2`(Int::class),
    `DayTradesRemainingT+3`(Int::class),
    `DayTradesRemainingT+4`(Int::class),
    EquityWithLoanValue(MoneyAmount::class),
    `EquityWithLoanValue-C`(MoneyAmount::class),
    `EquityWithLoanValue-S`(MoneyAmount::class),
    ExcessLiquidity(MoneyAmount::class),
    `ExcessLiquidity-C`(MoneyAmount::class),
    `ExcessLiquidity-S`(MoneyAmount::class),
    ExchangeRate(MoneyAmount::class),
    FullAvailableFunds(MoneyAmount::class),
    `FullAvailableFunds-C`(MoneyAmount::class),
    `FullAvailableFunds-S`(MoneyAmount::class),
    FullExcessLiquidity(MoneyAmount::class),
    `FullExcessLiquidity-C`(MoneyAmount::class),
    `FullExcessLiquidity-S`(MoneyAmount::class),
    FullInitMarginReq(MoneyAmount::class),
    `FullInitMarginReq-C`(MoneyAmount::class),
    `FullInitMarginReq-S`(MoneyAmount::class),
    FullMaintMarginReq(MoneyAmount::class),
    `FullMaintMarginReq-C`(MoneyAmount::class),
    `FullMaintMarginReq-S`(MoneyAmount::class),
    FundValue(MoneyAmount::class),
    FutureOptionValue(MoneyAmount::class),
    FuturesPNL(MoneyAmount::class),
    FxCashBalance(MoneyAmount::class),
    Guarantee(MoneyAmount::class),
    `Guarantee-C`(MoneyAmount::class),
    `Guarantee-S`(MoneyAmount::class),
    GrossPositionValue(MoneyAmount::class),
    `GrossPositionValue-S`(MoneyAmount::class),
    HighestSeverity(String::class),
    IndianStockHaircut(MoneyAmount::class),
    `IndianStockHaircut-C`(MoneyAmount::class),
    `IndianStockHaircut-S`(MoneyAmount::class),
    InitMarginReq(MoneyAmount::class),
    `InitMarginReq-C`(MoneyAmount::class),
    `InitMarginReq-S`(MoneyAmount::class),
    IssuerOptionValue(MoneyAmount::class),
    Leverage(MoneyAmount::class),
    `Leverage-S`(MoneyAmount::class),
    LookAheadAvailableFunds(MoneyAmount::class),
    `LookAheadAvailableFunds-C`(MoneyAmount::class),
    `LookAheadAvailableFunds-S`(MoneyAmount::class),
    LookAheadExcessLiquidity(MoneyAmount::class),
    `LookAheadExcessLiquidity-C`(MoneyAmount::class),
    `LookAheadExcessLiquidity-S`(MoneyAmount::class),
    LookAheadInitMarginReq(MoneyAmount::class),
    `LookAheadInitMarginReq-C`(MoneyAmount::class),
    `LookAheadInitMarginReq-S`(MoneyAmount::class),
    LookAheadMaintMarginReq(MoneyAmount::class),
    `LookAheadMaintMarginReq-C`(MoneyAmount::class),
    `LookAheadMaintMarginReq-S`(MoneyAmount::class),
    LookAheadNextChange(Int::class),
    MaintMarginReq(MoneyAmount::class),
    `MaintMarginReq-C`(MoneyAmount::class),
    `MaintMarginReq-S`(MoneyAmount::class),
    MoneyMarketFundValue(MoneyAmount::class),
    MutualFundValue(MoneyAmount::class),
    NetDividend(MoneyAmount::class),
    NetLiquidation(MoneyAmount::class),
    `NetLiquidation-C`(MoneyAmount::class),
    `NetLiquidation-S`(MoneyAmount::class),
    NetLiquidationByCurrency(MoneyAmount::class),
    NetLiquidationUncertainty(MoneyAmount::class),
    NLVAndMarginInReview(Boolean::class),
    OptionMarketValue(MoneyAmount::class),
    PASharesValue(MoneyAmount::class),
    `PASharesValue-C`(MoneyAmount::class),
    `PASharesValue-S`(MoneyAmount::class),
    PostExpirationExcess(MoneyAmount::class),
    `PostExpirationExcess-C`(MoneyAmount::class),
    `PostExpirationExcess-S`(MoneyAmount::class),
    PostExpirationMargin(MoneyAmount::class),
    `PostExpirationMargin-C`(MoneyAmount::class),
    `PostExpirationMargin-S`(MoneyAmount::class),
    PreviousDayEquityWithLoanValue(MoneyAmount::class),
    `PreviousDayEquityWithLoanValue-S`(MoneyAmount::class),
    PreviousEquityWithLoanValue(MoneyAmount::class),
    RealCurrency(java.util.Currency::class),
    RealizedPnL(MoneyAmount::class),
    RegTEquity(MoneyAmount::class),
    `RegTEquity-S`(MoneyAmount::class),
    RegTMargin(MoneyAmount::class),
    `RegTMargin-S`(MoneyAmount::class),
    SegmentTitle(String::class),
    `SegmentTitle-C`(String::class),
    `SegmentTitle-S`(String::class),
    SettledCash(MoneyAmount::class),
    SMA(MoneyAmount::class),
    `SMA-S`(MoneyAmount::class),
    StockMarketValue(MoneyAmount::class),
    TBondValue(MoneyAmount::class),
    TBillValue(MoneyAmount::class),
    TotalCashBalance(MoneyAmount::class),
    TotalCashValue(MoneyAmount::class),
    `TotalCashValue-C`(MoneyAmount::class),
    `TotalCashValue-S`(MoneyAmount::class),
    TotalDebitCardPendingCharges(MoneyAmount::class),
    `TotalDebitCardPendingCharges-C`(MoneyAmount::class),
    `TotalDebitCardPendingCharges-S`(MoneyAmount::class),
    `TradingType-S`(String::class),
    UnrealizedPnL(MoneyAmount::class),
    WarrantValue(MoneyAmount::class),
    WhatIfPMEnabled(Boolean::class);

    companion object {
        operator fun invoke(key: String): AccountField = try {
            valueOf(key)
        } catch (e: IllegalArgumentException) {
            throw AccountFieldNotFoundException(key)
        }
    }
}
