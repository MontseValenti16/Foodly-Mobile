package com.montse.apptransaccional.features.tables.domain.usecases

import com.montse.apptransaccional.features.tables.domain.repositories.TableRepository

class GetTablesUseCase(private val repository: TableRepository) {
    suspend operator fun invoke() = repository.getTables()
}
