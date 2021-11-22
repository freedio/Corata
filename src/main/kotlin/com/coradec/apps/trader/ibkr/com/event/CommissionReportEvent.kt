package com.coradec.apps.trader.ibkr.com.event

import com.coradec.coradeck.core.model.Origin
import com.ib.client.CommissionReport

class CommissionReportEvent(origin: Origin, commissionReport: CommissionReport) : InteractiveEvent(origin)
