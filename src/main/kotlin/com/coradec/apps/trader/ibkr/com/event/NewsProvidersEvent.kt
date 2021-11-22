package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin
import com.ib.client.NewsProvider

class NewsProvidersEvent(origin: Origin, val newsProviders: List<NewsProvider>): InteractiveEvent(origin)
