package com.coradec.apps.trader.ibkr.trouble

import com.coradec.apps.trader.model.QuoteType
import com.coradec.apps.trader.model.Title

class UnhandledException(message: String?, val title: Title, quoteType: QuoteType) : InteractiveException(message, null)
