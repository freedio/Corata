/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model

import com.coradec.coratrade.interactive.model.impl.BasicDataWindow

interface DataWindow {
    val size: Int
    val unit: TimeUnit

    companion object {
        fun of(size: Int, unit: TimeUnit) = BasicDataWindow(size, unit)
    }
}
