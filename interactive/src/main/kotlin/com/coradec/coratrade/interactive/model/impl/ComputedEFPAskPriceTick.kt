/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model.impl

class ComputedEFPAskPriceTick(
        basePt: Double,
        fmtBasePt: String,
        implFuture: Double,
        holdDays: Int,
        futEnd: String,
        divImpact: Double,
        divtoEnd: Double
) : BasicEFPTick(basePt, fmtBasePt, implFuture, holdDays, futEnd, divImpact, divtoEnd)
