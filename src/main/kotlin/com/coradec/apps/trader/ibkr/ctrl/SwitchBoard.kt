package com.coradec.apps.trader.ibkr.ctrl

import com.coradec.apps.trader.ibkr.model.Accounts
import com.coradec.apps.trader.model.Frequency
import com.coradec.coradeck.com.model.Voucher
import com.coradec.coradeck.conf.model.LocalProperty
import com.coradec.coradeck.core.util.classname
import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import kotlin.reflect.KClass

@Suppress("ObjectPropertyName", "NonAsciiCharacters", "FunctionName")
object SwitchBoard {
    private val PROP_DEFAULT_FREQUENCY = LocalProperty<Frequency>("DefaultFrequency")
    private val properties = mutableMapOf<KClass<*>, Voucher<*>>()
    private lateinit var timeΔ: Duration
    private val accountList = mutableListOf<String>()

    val accounts: Voucher<Accounts> get() = lookup(Accounts::class)
    val nextOrder = AtomicInteger(0)
    val connectionAcknowledged = AtomicBoolean(false)
    val defaultFrequency: Frequency get() = PROP_DEFAULT_FREQUENCY.value

    operator fun <T: Any> set(name: KClass<T>, value: Voucher<T>) {
        properties[name] = value
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T: Any> lookup(klass: KClass<T>): Voucher<T> = properties[klass] as? Voucher<T>
        ?: throw IllegalArgumentException("No switchboard property of type ‹${klass.classname}› found")

    fun setTimeΔ(timeTWS: LocalDateTime) {
        timeΔ = Duration.between(LocalDateTime.now(), timeTWS)
    }

    fun addAccount(name: String) {
        accountList += name
    }

    fun listAccounts(): Sequence<String> = accountList.asSequence()
}
