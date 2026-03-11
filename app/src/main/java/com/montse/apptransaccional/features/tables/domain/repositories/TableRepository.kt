package com.montse.apptransaccional.features.tables.domain.repositories

import com.montse.apptransaccional.features.tables.domain.models.Table

interface TableRepository {
    suspend fun getTables(): List<Table>
    suspend fun createTable(number: Int, capacity: Int): Table
    suspend fun deleteTable(id: Int)
}
