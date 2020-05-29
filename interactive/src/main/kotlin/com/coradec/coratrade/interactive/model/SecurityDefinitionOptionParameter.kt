/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model

import com.coradec.coratrade.interactive.model.impl.BasicSecurityDefinitionOptionParameter

interface SecurityDefinitionOptionParameter {

    companion object {
        fun of(
                exchange: String,
                underlyingConId: Int,
                tradingClass: String,
                multiplier: String,
                expirations: Set<String>,
                strikes: Set<Double>
        ) = BasicSecurityDefinitionOptionParameter(exchange, underlyingConId, tradingClass, multiplier, expirations, strikes)

    }

}
