/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model.impl

import com.coradec.coratrade.interactive.model.News

class BasicNews(
        val time: String,
        val providerCode: String,
        val articleId: String,
        val headline: String
) : News {

}
