package com.montse.apptransaccional.features.tables.data.repositories

import com.montse.apptransaccional.core.network.RestaurantApi
import com.montse.apptransaccional.features.tables.data.datasources.remote.CreateTableRequest
import com.montse.apptransaccional.features.tables.domain.models.Table
import com.montse.apptransaccional.features.tables.domain.repositories.TableRepository

class TableRepositoryImpl(
    private val api: RestaurantApi
) : TableRepository {

    override suspend fun getTables(): List<Table> {
        return api.getTables().tables.map { it.toDomain() }
    }

    override suspend fun createTable(number: Int, capacity: Int): Table {
        return api.createTable(CreateTableRequest(number = number, capacity = capacity)).toDomain()
    }

    override suspend fun deleteTable(id: Int) {
        api.deleteTable(id)
    }

    private fun com.montse.apptransaccional.features.tables.data.datasources.remote.TableDto.toDomain() =
        Table(
            id = id,
            number = number,
            capacity = capacity,
            isActive = isActive == 1
        )
}
