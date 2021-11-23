package com.coradec.apps.trader.model.impl

import com.coradec.apps.trader.model.AccountDefinition
import com.coradec.apps.trader.model.AccountPurpose
import com.coradec.coradeck.db.annot.Primary
import com.coradec.coradeck.db.annot.Size

data class BasicAccountDefinition(
    override val name: @Primary @Size(15) String,
    override val purpose: AccountPurpose
) : AccountDefinition
