package com.montse.apptransaccional.features.dashboard.domain.usecases

import com.montse.apptransaccional.features.dashboard.domain.repositories.DishRepository

class DeleteDishUseCase(private val repository: DishRepository) {
    suspend operator fun invoke(id: Int) = repository.deleteDish(id)
}
