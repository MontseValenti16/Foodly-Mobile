package com.montse.apptransaccional.features.waiter.domain.usecases

import com.montse.apptransaccional.features.waiter.domain.repositories.WaiterRepository

class OpenSessionUseCase(private val repository: WaiterRepository) {
    suspend operator fun invoke(tableId: Int) = repository.openSession(tableId)
}
