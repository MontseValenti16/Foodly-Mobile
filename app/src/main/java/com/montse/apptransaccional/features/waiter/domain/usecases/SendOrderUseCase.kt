package com.montse.apptransaccional.features.waiter.domain.usecases

import com.montse.apptransaccional.features.waiter.domain.models.CartItem
import com.montse.apptransaccional.features.waiter.domain.repositories.WaiterRepository

class SendOrderUseCase(private val repository: WaiterRepository) {
    suspend operator fun invoke(sessionId: Int, items: List<CartItem>) =
        repository.sendOrder(sessionId, items)
}
