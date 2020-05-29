/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.log

import com.coradec.coradeck.com.model.LogEntry
import com.coradec.coradeck.com.model.LogLevel
import com.coradec.coradeck.core.ctrl.Origin
import com.coradec.coratrade.interactive.comm.model.impl.BasicResponseEvent
import java.time.LocalDateTime.now

class TWSErrorLogEntry(origin: Origin, requestId: Int, private val errorCode: Int, private val errorMsg: String) :
        BasicResponseEvent(origin, requestId, now()), LogEntry {
    override val level = LogLevel.ERROR

    override fun formattedWith(format: String): String =
        String.format("%tF %<tT.%<tL %5s %s",
                createdAt, level.abbrev, String.format("Request[%d]: error %d (\"%s\")", requestId, errorCode, errorMsg))

}
