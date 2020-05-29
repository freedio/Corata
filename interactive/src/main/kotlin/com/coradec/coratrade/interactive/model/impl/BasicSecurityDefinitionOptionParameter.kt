/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model.impl

import com.coradec.coratrade.interactive.model.SecurityDefinitionOptionParameter

class BasicSecurityDefinitionOptionParameter(
        val exchange: String,
        val underlyingConId: Int,
        val tradingClass: String,
        val multiplier: String,
        val expirations: Set<String>,
        val strikes: Set<Double>
): SecurityDefinitionOptionParameter
