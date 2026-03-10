package com.montse.apptransaccional.features.dashboard.domain.usecases

import com.montse.apptransaccional.features.dashboard.domain.models.Dish
import com.montse.apptransaccional.features.dashboard.domain.repositories.DishRepository
import javax.inject.Inject

class CreateDishUseCase @Inject constructor(private val repository: DishRepository) {
    suspend operator fun invoke(dish: Dish) = repository.createDish(dish)
}
