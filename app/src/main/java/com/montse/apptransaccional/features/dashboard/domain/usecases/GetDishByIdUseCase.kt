package com.montse.apptransaccional.features.dashboard.domain.usecases

import com.montse.apptransaccional.features.dashboard.domain.repositories.DishRepository
import javax.inject.Inject

class GetDishByIdUseCase @Inject constructor(private val repository: DishRepository) {
    suspend operator fun invoke(id: Int) = repository.getDishById(id)
}
