package com.montse.apptransaccional.features.dashboard.di

import android.content.Context
import com.montse.apptransaccional.core.network.RestaurantApi
import com.montse.apptransaccional.features.dashboard.data.repositories.AreaRepositoryImpl
import com.montse.apptransaccional.features.dashboard.data.repositories.CategoryRepositoryImpl
import com.montse.apptransaccional.features.dashboard.data.repositories.DishRepositoryImpl
import com.montse.apptransaccional.features.dashboard.domain.repositories.AreaRepository
import com.montse.apptransaccional.features.dashboard.domain.repositories.CategoryRepository
import com.montse.apptransaccional.features.dashboard.domain.repositories.DishRepository
import com.montse.apptransaccional.features.dashboard.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DashboardModule {

    @Provides
    @Singleton
    fun provideDishRepository(
        api: RestaurantApi,
        @ApplicationContext context: Context
    ): DishRepository {
        return DishRepositoryImpl(api, context)
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(api: RestaurantApi): CategoryRepository {
        return CategoryRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideAreaRepository(api: RestaurantApi): AreaRepository {
        return AreaRepositoryImpl(api)
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

    @Provides
    @Singleton
    fun provideGetCategoriesUseCase(repository: CategoryRepository): GetCategoriesUseCase {
        return GetCategoriesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetAreasUseCase(repository: AreaRepository): GetAreasUseCase {
        return GetAreasUseCase(repository)
    }
}
