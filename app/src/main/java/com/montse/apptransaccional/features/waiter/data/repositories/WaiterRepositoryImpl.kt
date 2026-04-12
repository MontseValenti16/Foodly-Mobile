package com.montse.apptransaccional.features.waiter.data.repositories

import com.montse.apptransaccional.core.network.RestaurantApi
import com.montse.apptransaccional.features.waiter.data.datasources.remote.CreateSessionRequest
import com.montse.apptransaccional.features.waiter.domain.models.TableStatus
import com.montse.apptransaccional.features.waiter.domain.models.WaiterTable
import com.montse.apptransaccional.features.waiter.domain.repositories.WaiterRepository

class WaiterRepositoryImpl(
    private val api: RestaurantApi
) : WaiterRepository {

    override suspend fun getTablesWithStatus(): List<WaiterTable> {
        val tables = api.getTables().tables
        val openSessions = api.getSessions(status = "open")

        val sessionByTableId = openSessions.associateBy { it.tableId }

        return tables
            .filter { it.isActive == 1 }
            .map { table ->
                val session = sessionByTableId[table.id]
                WaiterTable(
                    id = table.id,
                    number = table.number,
                    capacity = table.capacity,
                    status = if (session != null) TableStatus.OCUPADA else TableStatus.LIBRE,
                    sessionId = session?.id,
                    waiterName = session?.waiterName
                )
            }
            .sortedBy { it.number }
    }

    override suspend fun openSession(tableId: Int): Int {
        val session = api.createSession(CreateSessionRequest(tableId = tableId))
        return session.id
    }
}
