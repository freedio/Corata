/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model.impl

import com.coradec.coratrade.interactive.model.AccountModelUpdate

class BasicAccountModelUpdate(
        val accountId: String?,
        val modelCode: String?,
        val key: String,
        val value: String,
        val currency: String?
) : AccountModelUpdate
