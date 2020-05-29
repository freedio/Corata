/*
 * Copyright ⓒ 2020. by Coradec LLC. All rights reserved.
 */

package com.coradec.coratrade.interactive.model

import com.coradec.coratrade.interactive.model.impl.BasicScannerData
import com.ib.client.ContractDetails

interface ScannerData {
    companion object {
        fun of(
                rank: Int,
                contractDetails: ContractDetails,
                distance: String,
                benchmark: String,
                projection: String,
                combolegs: String
        ): ScannerData = BasicScannerData(rank, contractDetails, distance, benchmark, projection, combolegs)
    }
}
