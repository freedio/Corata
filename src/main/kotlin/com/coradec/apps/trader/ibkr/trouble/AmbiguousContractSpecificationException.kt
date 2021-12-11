package com.coradec.apps.trader.ibkr.trouble

import com.coradec.apps.trader.model.Title

class AmbiguousContractSpecificationException(message: String?, val title: Title) : InteractiveException(message, null) {

}
