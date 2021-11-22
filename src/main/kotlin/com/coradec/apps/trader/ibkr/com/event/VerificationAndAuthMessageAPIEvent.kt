package com.coradec.apps.trader.ibkr.com.event

import com.coradec.apps.trader.ibkr.com.event.BasicVerificationEvent
import com.coradec.coradeck.core.model.Origin

class VerificationAndAuthMessageAPIEvent(origin: Origin, apiData: String, xyzChallenge: String): BasicVerificationEvent(origin)
