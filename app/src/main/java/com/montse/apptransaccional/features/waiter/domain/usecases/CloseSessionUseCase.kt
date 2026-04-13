package com.montse.apptransaccional.features.waiter.domain.usecases

import com.montse.apptransaccional.features.waiter.domain.repositories.WaiterRepository

class CloseSessionUseCase(private val repository: WaiterRepository) {
    suspend operator fun invoke(
        sessionId: Int,
        paymentMethod: String = "efectivo",
        tip: Double = 0.0,
        discount: Double = 0.0,
        notes: String? = null
    ) = repository.closeAndPrintTicket(sessionId, paymentMethod, tip, discount, notes)
}
