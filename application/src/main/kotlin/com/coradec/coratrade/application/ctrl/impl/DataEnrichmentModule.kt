/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.application.ctrl.impl

import com.coradec.coradeck.bus.model.impl.BasicBusModule

class DataEnrichmentModule: BasicBusModule() {
    override fun onReady() {
        super.onReady()
        finish()
    }
}
