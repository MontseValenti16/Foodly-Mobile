package com.montse.apptransaccional.features.tables.domain.usecases

import com.montse.apptransaccional.features.tables.domain.repositories.TableRepository

class CreateTableUseCase(private val repository: TableRepository) {
    suspend operator fun invoke(number: Int, capacity: Int) =
        repository.createTable(number, capacity)
}
