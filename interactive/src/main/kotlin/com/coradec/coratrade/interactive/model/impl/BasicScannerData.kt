/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model.impl

import com.coradec.coratrade.interactive.model.ScannerData
import com.ib.client.ContractDetails

class BasicScannerData(
        val rank: Int,
        val contractDetails: ContractDetails,
        val distance: String,
        val benchmark: String,
        val projection: String,
        val combolegs: String
) : ScannerData
