package com.montse.apptransaccional.features.dashboard.domain.repositories

import com.montse.apptransaccional.features.dashboard.domain.models.Dish

interface DishRepository {
    suspend fun getDishes(): List<Dish>
    suspend fun getDishById(id: Int): Dish
    suspend fun createDish(dish: Dish): Dish
    suspend fun updateDish(dish: Dish)
    suspend fun deleteDish(id: Int)
}
