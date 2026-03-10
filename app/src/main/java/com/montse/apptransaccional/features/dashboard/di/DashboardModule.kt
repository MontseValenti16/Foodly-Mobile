package com.montse.apptransaccional.features.dashboard.di

import com.montse.apptransaccional.core.network.RestaurantApi
import com.montse.apptransaccional.features.dashboard.data.repositories.DishRepositoryImpl
import com.montse.apptransaccional.features.dashboard.domain.repositories.DishRepository
import com.montse.apptransaccional.features.dashboard.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DashboardModule {

    @Provides
    @Singleton
    fun provideDishRepository(api: RestaurantApi): DishRepository {
        return DishRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideGetDishesUseCase(repository: DishRepository): GetDishesUseCase {
        return GetDishesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetDishByIdUseCase(repository: DishRepository): GetDishByIdUseCase {
        return GetDishByIdUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideCreateDishUseCase(repository: DishRepository): CreateDishUseCase {
        return CreateDishUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUpdateDishUseCase(repository: DishRepository): UpdateDishUseCase {
        return UpdateDishUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteDishUseCase(repository: DishRepository): DeleteDishUseCase {
        return DeleteDishUseCase(repository)
    }
}
