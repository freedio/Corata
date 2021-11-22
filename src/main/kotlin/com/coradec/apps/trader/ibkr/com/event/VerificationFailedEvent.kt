package com.coradec.apps.trader.ibkr.com.event

import com.coradec.apps.trader.ibkr.com.event.BasicVerificationEvent
import com.coradec.coradeck.core.model.Origin

class VerificationFailedEvent(origin: Origin, val errorText: String): BasicVerificationEvent(origin)
