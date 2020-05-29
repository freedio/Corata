/*
 * Copyright © 2019 by Coradec LLC.  All rights reserved.
 */

package com.coradec.coratrade.application.model

import com.coradec.coratrade.interactive.ctrl.RequestDispatcher
import java.time.Duration

object Switchboard {
    val orderId: Int get() = RequestDispatcher.orderId
    val serverTimeLag: Duration get() = RequestDispatcher.serverTimeLag.Δt
    val managedAccounts get() = RequestDispatcher.managedAccounts.value()
}
