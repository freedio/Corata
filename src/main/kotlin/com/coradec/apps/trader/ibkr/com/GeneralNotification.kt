package com.coradec.apps.trader.ibkr.com

import com.coradec.apps.trader.ibkr.com.event.InteractiveEvent
import com.coradec.coradeck.core.model.Origin

class GeneralNotification(origin: Origin, val code: Int, val message: String) : InteractiveEvent(origin)
