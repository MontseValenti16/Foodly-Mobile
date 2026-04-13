package com.montse.apptransaccional.features.waiter.domain.usecases

import com.montse.apptransaccional.features.waiter.domain.repositories.WaiterRepository

class UpdateItemStatusUseCase(private val repository: WaiterRepository) {
    suspend operator fun invoke(itemId: Int, status: String) =
        repository.updateItemStatus(itemId, status)
}
