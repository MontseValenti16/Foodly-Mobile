package com.montse.apptransaccional.features.dashboard.domain.usecases

import com.montse.apptransaccional.features.dashboard.domain.repositories.DishRepository
import javax.inject.Inject

class GetDishesUseCase @Inject constructor(private val repository: DishRepository) {
    suspend operator fun invoke() = repository.getDishes()
}
