/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.model.impl

import com.coradec.coratrade.interactive.comm.model.Amount
import java.util.*

class BasicAmount(override val magnitude: Double, override val currency: Currency) : Amount
