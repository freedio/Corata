/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model

import com.coradec.coratrade.interactive.model.impl.BasicNews

interface News {
    companion object {
        fun from(time: String, providerCode: String, articleId: String, headline: String): News =
                BasicNews(time, providerCode, articleId, headline)
    }
}
