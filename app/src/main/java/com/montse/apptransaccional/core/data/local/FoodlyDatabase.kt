package com.montse.apptransaccional.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.montse.apptransaccional.features.dashboard.data.datasources.local.DishDao
import com.montse.apptransaccional.features.dashboard.data.datasources.local.DishEntity
import com.montse.apptransaccional.features.users.data.local.daos.UserDao
import com.montse.apptransaccional.features.users.data.local.entities.UserEntity

@Database(
    entities = [UserEntity::class, DishEntity::class],
    version = 2,
    exportSchema = false
)
abstract class FoodlyDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun dishDao(): DishDao
}
