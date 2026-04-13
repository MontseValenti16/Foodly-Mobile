package com.montse.apptransaccional.features.dashboard.domain.usecases

import android.net.Uri
import com.montse.apptransaccional.features.dashboard.domain.models.Dish
import com.montse.apptransaccional.features.dashboard.domain.repositories.DishRepository
import javax.inject.Inject

class UpdateDishUseCase @Inject constructor(private val repository: DishRepository) {
    suspend operator fun invoke(dish: Dish, imageUri: Uri? = null) {
        repository.updateDish(dish, imageUri)
    }
}
