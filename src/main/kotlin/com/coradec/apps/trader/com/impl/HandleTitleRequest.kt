package com.coradec.apps.trader.com.impl

import com.coradec.apps.trader.model.Title
import com.coradec.coradeck.com.model.impl.BasicRequest
import com.coradec.coradeck.core.model.Origin

/** Handle the specified active title. */
class HandleTitleRequest(origin: Origin, val title: Title): BasicRequest(origin)
