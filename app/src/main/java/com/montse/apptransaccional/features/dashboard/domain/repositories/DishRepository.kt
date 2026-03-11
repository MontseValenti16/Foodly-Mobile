package com.montse.apptransaccional.features.dashboard.domain.repositories

import android.net.Uri
import com.montse.apptransaccional.features.dashboard.domain.models.Dish

interface DishRepository {
    suspend fun getDishes(): List<Dish>
    suspend fun getDishById(id: Int): Dish
    suspend fun createDish(dish: Dish, imageUri: Uri?): Dish
    suspend fun updateDish(dish: Dish, imageUri: Uri?)
    suspend fun deleteDish(id: Int)
}
