/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model.impl

import com.coradec.coratrade.interactive.model.NewsBulletin

class BasicNewBulletin(
        val messageId: Int,
        val messageType: Int,
        val message: String,
        val origExch: String
) : NewsBulletin {

}
