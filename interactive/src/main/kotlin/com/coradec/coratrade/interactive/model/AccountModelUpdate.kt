/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model

import com.coradec.coratrade.interactive.model.impl.BasicAccountModelUpdate

interface AccountModelUpdate {

    companion object {
        fun of(accountId: String?, modelCode: String?, key: String, value: String, currency: String?): AccountModelUpdate =
                BasicAccountModelUpdate(accountId, modelCode, key, value, currency)
    }
}
