package com.montse.apptransaccional.features.dashboard.domain.usecases

import com.montse.apptransaccional.features.dashboard.domain.repositories.DishRepository

class GetDishesUseCase(private val repository: DishRepository) {
    suspend operator fun invoke() = repository.getDishes()
}
