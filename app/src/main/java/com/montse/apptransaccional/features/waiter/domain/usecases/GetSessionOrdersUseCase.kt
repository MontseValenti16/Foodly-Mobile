package com.montse.apptransaccional.features.waiter.domain.usecases

import com.montse.apptransaccional.features.waiter.domain.repositories.WaiterRepository

class GetSessionOrdersUseCase(private val repository: WaiterRepository) {
    suspend operator fun invoke(sessionId: Int) = repository.getSessionOrders(sessionId)
}
