package com.montse.apptransaccional.features.waiter.domain.usecases

import com.montse.apptransaccional.features.waiter.domain.repositories.WaiterRepository

class GetWaiterTablesUseCase(private val repository: WaiterRepository) {
    suspend operator fun invoke() = repository.getTablesWithStatus()
}
