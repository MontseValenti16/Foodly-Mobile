package com.montse.apptransaccional.features.waiter.di

import com.montse.apptransaccional.core.network.RestaurantApi
import com.montse.apptransaccional.features.waiter.data.repositories.WaiterRepositoryImpl
import com.montse.apptransaccional.features.waiter.domain.repositories.WaiterRepository
import com.montse.apptransaccional.features.waiter.domain.usecases.CloseSessionUseCase
import com.montse.apptransaccional.features.waiter.domain.usecases.GetProductsUseCase
import com.montse.apptransaccional.features.waiter.domain.usecases.UpdateItemStatusUseCase
import com.montse.apptransaccional.features.waiter.domain.usecases.GetSessionOrdersUseCase
import com.montse.apptransaccional.features.waiter.domain.usecases.GetWaiterCategoriesUseCase
import com.montse.apptransaccional.features.waiter.domain.usecases.GetWaiterTablesUseCase
import com.montse.apptransaccional.features.waiter.domain.usecases.OpenSessionUseCase
import com.montse.apptransaccional.features.waiter.domain.usecases.SendOrderUseCase
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

    @Provides
    @Singleton
    fun provideGetProductsUseCase(repository: WaiterRepository): GetProductsUseCase {
        return GetProductsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetWaiterCategoriesUseCase(repository: WaiterRepository): GetWaiterCategoriesUseCase {
        return GetWaiterCategoriesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetSessionOrdersUseCase(repository: WaiterRepository): GetSessionOrdersUseCase {
        return GetSessionOrdersUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSendOrderUseCase(repository: WaiterRepository): SendOrderUseCase {
        return SendOrderUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUpdateItemStatusUseCase(repository: WaiterRepository): UpdateItemStatusUseCase {
        return UpdateItemStatusUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideCloseSessionUseCase(repository: WaiterRepository): CloseSessionUseCase {
        return CloseSessionUseCase(repository)
    }
}
