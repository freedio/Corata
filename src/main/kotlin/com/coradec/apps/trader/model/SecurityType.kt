package com.coradec.apps.trader.model

import com.coradec.apps.trader.trouble.SecurityTypeNotFoundException
import com.coradec.coradeck.core.model.SqlTransformable

enum class SecurityType(val code: String): SqlTransformable {
    None("None"),
    Stock("STK"),
    Option("OPT"),
    Future("FUT"),
    ContinuousFuture("CONTFUT"),
    FxPair("CASH"),
    Bond("BOND"),
    CFD("CFD"),
    FutureOptions("FOP"),
    Warrant("WAR"),
    StructuredProduct("IOPT"),
    Forwards("FWD"),
    Bag("BAG"),
    Index("IND"),
    Bill("BILL"),
    MutualFund("FUND"),
    Fixed("FIXED"),
    SLB("SLB"),
    News("NEWS"),
    Commodity("CMDTY"),
    Basket("BSK"),
    ICU("ICU"),
    ICS("ICS"),
    CryptoCurrency("CRYPTO");

    override fun toSqlValue(): String = ordinal.toString()

    companion object {
        val sqlType = "TINYINT"
        fun fromSql(value: Int) = values().single { it.ordinal == value }

        operator fun invoke(code: String) = values().singleOrNull { it.code == code } ?: throw SecurityTypeNotFoundException(code)
    }
}
