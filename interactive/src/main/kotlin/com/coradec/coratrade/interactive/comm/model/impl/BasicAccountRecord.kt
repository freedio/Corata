/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.model.impl

import com.coradec.coradeck.com.ctrl.impl.Logger
import com.coradec.coradeck.text.model.LocalizedText
import com.coradec.coratrade.interactive.comm.model.AccountRecord
import com.coradec.coratrade.interactive.comm.model.Amount
import com.coradec.coratrade.interactive.model.AccountField
import com.coradec.coratrade.interactive.model.AccountSummary
import com.coradec.coratrade.interactive.trouble.NoSuchAccountFieldException
import java.util.*

class BasicAccountRecord(val name: String) : Logger(), AccountRecord {
    private val properties = mutableMapOf<AccountField, Any>()
    override val entries: Map<AccountField, Any> get() = Collections.unmodifiableMap(properties.toMap())

    override fun get(field: AccountField): Any? = properties[field]
    override fun plusAssign(data: AccountSummary) {
        try {
            val accountField = AccountField.from(data.tag)
            properties[accountField] = valueOf(accountField, data.value, data.currency)
        } catch (e: NoSuchAccountFieldException) {
            error(e)
        }
    }

    override fun updateFrom(from: AccountRecord) {
        properties.clear()
        from.entries.forEach { (key, value) ->
            properties[key] = value
        }
    }

    private fun valueOf(type: AccountField, value: String, ccy: String?): Any =
            if (ccy != null && type != AccountField.Currency) {
                var currency: Currency? = null
                try {
                    currency = if (ccy == "BASE") Currency.getInstance(Locale.getDefault()) else Currency.getInstance(ccy)
                } catch (e: IllegalArgumentException) {
                    error(TEXT_INVALID_CURRENCY_IGNORED, ccy)
                }
                val magnitude = value.toDoubleOrNull()
                if (magnitude != null && currency != null) Amount.of(magnitude, currency) else value
            } else value

    companion object {
        private val TEXT_INVALID_CURRENCY_IGNORED = LocalizedText.define("InvalidCurrencyIgnored")
    }

}
