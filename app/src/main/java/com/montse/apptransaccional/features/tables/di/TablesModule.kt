package com.montse.apptransaccional.features.tables.di

import com.montse.apptransaccional.core.network.RestaurantApi
import com.montse.apptransaccional.features.tables.data.repositories.TableRepositoryImpl
import com.montse.apptransaccional.features.tables.domain.repositories.TableRepository
import com.montse.apptransaccional.features.tables.domain.usecases.CreateTableUseCase
import com.montse.apptransaccional.features.tables.domain.usecases.DeleteTableUseCase
import com.montse.apptransaccional.features.tables.domain.usecases.GetTablesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TablesModule {

    @Provides
    @Singleton
    fun provideTableRepository(api: RestaurantApi): TableRepository {
        return TableRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideGetTablesUseCase(repository: TableRepository): GetTablesUseCase {
        return GetTablesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideCreateTableUseCase(repository: TableRepository): CreateTableUseCase {
        return CreateTableUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteTableUseCase(repository: TableRepository): DeleteTableUseCase {
        return DeleteTableUseCase(repository)
    }
}
