package com.montse.apptransaccional.core.di

import android.content.Context
import androidx.room.Room
import com.montse.apptransaccional.core.data.local.FoodlyDatabase
import com.montse.apptransaccional.features.dashboard.data.datasources.local.DishDao
import com.montse.apptransaccional.features.users.data.local.daos.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): FoodlyDatabase {
        return Room.databaseBuilder(
            context,
            FoodlyDatabase::class.java,
            "foodly_db"
        )
        .fallbackToDestructiveMigration() // Agregado para manejar cambios en la versión de la BD
        .build()
    }

    @Provides
    fun provideUserDao(db: FoodlyDatabase): UserDao {
        return db.userDao()
    }

    @Provides
    fun provideDishDao(db: FoodlyDatabase): DishDao {
        return db.dishDao()
    }
}
