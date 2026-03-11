package com.montse.apptransaccional.features.tables.domain.usecases

import com.montse.apptransaccional.features.tables.domain.repositories.TableRepository

class DeleteTableUseCase(private val repository: TableRepository) {
    suspend operator fun invoke(id: Int) = repository.deleteTable(id)
}
