/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model

import com.coradec.coratrade.interactive.model.impl.BasicNewsArticle

interface NewsArticle {
    companion object {
        fun of(articleType: Int, articleText: String): NewsArticle = BasicNewsArticle(articleType, articleText)
    }
}
