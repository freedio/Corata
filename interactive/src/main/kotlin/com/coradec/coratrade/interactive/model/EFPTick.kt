/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model

import com.coradec.coradeck.core.trouble.InvalidArgumentException
import com.coradec.coratrade.interactive.model.impl.*

interface EFPTick: Tick {

    companion object {
        fun of(
                tickType: Int,
                basePt: Double,
                fmtBasePt: String,
                implFuture: Double,
                holdDays: Int,
                futEnd: String,
                divImpact: Double,
                divtoEnd: Double): EFPTick = when (tickType) {
            38 -> ComputedEFPBidPriceTick(basePt, fmtBasePt, implFuture, holdDays, futEnd, divImpact, divtoEnd)
            39 -> ComputedEFPAskPriceTick(basePt, fmtBasePt, implFuture, holdDays, futEnd, divImpact, divtoEnd)
            40 -> ComputedEFPLastPriceTick(basePt, fmtBasePt, implFuture, holdDays, futEnd, divImpact, divtoEnd)
            41 -> ComputedEFPOpenPriceTick(basePt, fmtBasePt, implFuture, holdDays, futEnd, divImpact, divtoEnd)
            42 -> ComputedEFPHighPriceTick(basePt, fmtBasePt, implFuture, holdDays, futEnd, divImpact, divtoEnd)
            43 -> ComputedEFPLowPriceTick(basePt, fmtBasePt, implFuture, holdDays, futEnd, divImpact, divtoEnd)
            44 -> ComputedEFPClosePriceTick(basePt, fmtBasePt, implFuture, holdDays, futEnd, divImpact, divtoEnd)
            else -> throw InvalidArgumentException(tickType, "Illegal EFP tick type!")
        }

    }

}
