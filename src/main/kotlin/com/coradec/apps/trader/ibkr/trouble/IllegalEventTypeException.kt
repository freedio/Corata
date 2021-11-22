package com.coradec.apps.trader.ibkr.trouble

import com.coradec.coradeck.com.model.Information

class IllegalEventTypeException(val event: Information) : InteractiveException() {

}
