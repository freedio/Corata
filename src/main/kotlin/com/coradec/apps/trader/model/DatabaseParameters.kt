package com.coradec.apps.trader.model

import com.coradec.coradeck.type.model.Password
import java.net.URI

data class DatabaseParameters(
    val URI: URI,
    val Username: String,
    val Password: Password
)
