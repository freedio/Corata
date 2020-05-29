/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.model.impl

import com.coradec.coradeck.core.ctrl.Origin
import com.coradec.coratrade.interactive.model.ScannerData
import java.time.LocalDateTime.now

class ScannerDataEvent(origin: Origin, requestId: Int, val scannerData: ScannerData):
        BasicResponseEvent(origin, requestId, now())
