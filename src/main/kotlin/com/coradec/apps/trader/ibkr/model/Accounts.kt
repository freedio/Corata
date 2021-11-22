package com.coradec.apps.trader.ibkr.model

data class Accounts(val accounts: List<Account>): Iterable<Account>, Sequence<Account> {
    override fun iterator(): Iterator<Account>  = accounts.iterator()
}
