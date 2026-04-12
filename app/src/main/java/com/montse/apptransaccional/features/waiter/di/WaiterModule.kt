package com.montse.apptransaccional.features.waiter.di

import com.montse.apptransaccional.core.network.RestaurantApi
import com.montse.apptransaccional.features.waiter.data.repositories.WaiterRepositoryImpl
import com.montse.apptransaccional.features.waiter.domain.repositories.WaiterRepository
import com.montse.apptransaccional.features.waiter.domain.usecases.GetWaiterTablesUseCase
import com.montse.apptransaccional.features.waiter.domain.usecases.OpenSessionUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WaiterModule {

    @Provides
    @Singleton
    fun provideWaiterRepository(api: RestaurantApi): WaiterRepository {
        return WaiterRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideGetWaiterTablesUseCase(repository: WaiterRepository): GetWaiterTablesUseCase {
        return GetWaiterTablesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideOpenSessionUseCase(repository: WaiterRepository): OpenSessionUseCase {
        return OpenSessionUseCase(repository)
    }
}
