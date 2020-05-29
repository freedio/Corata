/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.model.impl

import com.coradec.coradeck.core.ctrl.Origin
import com.coradec.coratrade.interactive.model.News
import java.time.LocalDateTime.now

class HistoricalNewsevent(origin: Origin, requestId: Int, val news: News) : BasicResponseEvent(origin, requestId, now())
