package com.coradec.apps.trader.ibkr.com.event

import com.coradec.apps.trader.ibkr.model.ArticleType
import com.coradec.coradeck.core.model.Origin

class NewsArticleEvent(
    origin: Origin, requestId: Int, val type: ArticleType, val text: String
): BasicRequestEvent(origin, requestId)
