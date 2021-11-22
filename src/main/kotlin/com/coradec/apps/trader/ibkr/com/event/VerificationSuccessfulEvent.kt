package com.coradec.apps.trader.ibkr.com.event

import com.coradec.apps.trader.ibkr.com.event.BasicVerificationEvent
import com.coradec.coradeck.core.model.Origin

class VerificationSuccessfulEvent(origin: Origin): BasicVerificationEvent(origin)
