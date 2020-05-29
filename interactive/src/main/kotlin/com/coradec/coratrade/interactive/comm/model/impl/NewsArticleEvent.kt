/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.comm.model.impl

import com.coradec.coradeck.core.ctrl.Origin
import com.coradec.coratrade.interactive.model.NewsArticle
import java.time.LocalDateTime.now

class NewsArticleEvent(origin: Origin, requestId: Int, val newsArticle: NewsArticle):
        BasicResponseEvent(origin, requestId, now())
