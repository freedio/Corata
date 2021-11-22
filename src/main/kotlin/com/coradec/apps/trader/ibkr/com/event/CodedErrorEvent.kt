package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin

class CodedErrorEvent(
    origin: Origin, val requestId: Int, val errorCode: Int, message: String
) : ErrorEvent(origin, message = message)
