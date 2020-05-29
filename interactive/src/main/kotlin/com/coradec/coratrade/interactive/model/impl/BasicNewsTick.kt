/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model.impl

import com.coradec.coratrade.interactive.model.NewsTick
import java.time.ZonedDateTime

class BasicNewsTick(
        val systime: ZonedDateTime,
        val providerCode: String,
        val articleId: String,
        val headline: String,
        val extraData: String
) : NewsTick
