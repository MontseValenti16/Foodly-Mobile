package com.montse.apptransaccional.core.di

import android.content.Context
import androidx.room.Room
import com.montse.apptransaccional.core.database.AppDatabase
import com.montse.apptransaccional.features.dashboard.data.datasources.local.DishDao
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
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "foodly_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideDishDao(database: AppDatabase): DishDao {
        return database.dishDao()
    }
}
