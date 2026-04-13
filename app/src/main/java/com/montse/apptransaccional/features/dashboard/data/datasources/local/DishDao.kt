package com.montse.apptransaccional.features.dashboard.data.datasources.local

import androidx.room.*

@Dao
interface DishDao {
    @Query("SELECT * FROM dishes")
    suspend fun getAllDishes(): List<DishEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDishes(dishes: List<DishEntity>)

    @Query("DELETE FROM dishes")
    suspend fun deleteAllDishes()

    @Query("SELECT * FROM dishes WHERE id = :id")
    suspend fun getDishById(id: Int): DishEntity?

    @Delete
    suspend fun deleteDish(dish: DishEntity)
}
