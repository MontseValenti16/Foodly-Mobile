package com.montse.apptransaccional.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.montse.apptransaccional.features.dashboard.data.datasources.local.DishDao
import com.montse.apptransaccional.features.dashboard.data.datasources.local.DishEntity

@Database(entities = [DishEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dishDao(): DishDao
}
