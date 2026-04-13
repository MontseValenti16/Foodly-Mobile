package com.montse.apptransaccional.features.dashboard.domain.usecases

import android.net.Uri
import com.montse.apptransaccional.features.dashboard.domain.models.Dish
import com.montse.apptransaccional.features.dashboard.domain.repositories.DishRepository

class CreateDishUseCase(private val repository: DishRepository) {
    suspend operator fun invoke(dish: Dish, imageUri: Uri?): Dish {
        return repository.createDish(dish, imageUri)
    }
}
