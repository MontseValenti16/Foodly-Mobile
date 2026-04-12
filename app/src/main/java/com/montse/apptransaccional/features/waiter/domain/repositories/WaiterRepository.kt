package com.montse.apptransaccional.features.waiter.domain.repositories

import com.montse.apptransaccional.features.waiter.domain.models.WaiterTable

interface WaiterRepository {
    suspend fun getTablesWithStatus(): List<WaiterTable>
    suspend fun openSession(tableId: Int): Int
}
