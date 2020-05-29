/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model

import com.coradec.coratrade.interactive.model.impl.BasicNewsTick
import java.time.ZonedDateTime

interface NewsTick: Tick {

    companion object {
        fun of(systime: ZonedDateTime, providerCode: String, articleId: String, headline: String, extraData: String): NewsTick =
                BasicNewsTick(systime, providerCode, articleId, headline, extraData)
    }

}
