package com.coradec.apps.trader.ibkr.com.event

import com.coradec.apps.trader.ibkr.com.event.FieldTickEvent
import com.coradec.coradeck.core.model.Origin
import com.ib.client.TickAttrib

class PriceTickEvent(origin: Origin, tickerId: Int, field: Int, val price: Double, val attributes: TickAttrib) :
    FieldTickEvent(origin, tickerId, field)
