/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model.impl

import com.coradec.coratrade.interactive.model.AccountSummary

class BasicAccountSummary(
        override val accountId: String,
        override val tag: String,
        override val value: String,
        override val currency: String?
) : AccountSummary
