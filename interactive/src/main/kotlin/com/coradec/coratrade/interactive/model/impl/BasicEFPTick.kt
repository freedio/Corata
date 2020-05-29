/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model.impl

import com.coradec.coratrade.interactive.model.EFPTick

open class BasicEFPTick(
        val basePt: Double,
        val fmtBasePt: String,
        val implFuture: Double,
        val holdDays: Int,
        val futEnd: String,
        val divImpact: Double,
        val divtoEnd: Double
) : EFPTick
