package com.montse.apptransaccional.features.auth.di

import com.montse.apptransaccional.core.network.RestaurantApi
import com.montse.apptransaccional.core.session.SessionManager
import com.montse.apptransaccional.features.auth.data.repositories.AuthRepositoryImpl
import com.montse.apptransaccional.features.auth.domain.repositories.AuthRepository
import com.montse.apptransaccional.features.auth.domain.usecases.LoginUseCase
import com.montse.apptransaccional.features.auth.domain.usecases.RegisterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        api: RestaurantApi,
        sessionManager: SessionManager
    ): AuthRepository {
        return AuthRepositoryImpl(api, sessionManager)
    }

    @Provides
    @Singleton
    fun provideLoginUseCase(repository: AuthRepository): LoginUseCase {
        return LoginUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideRegisterUseCase(repository: AuthRepository): RegisterUseCase {
        return RegisterUseCase(repository)
    }
}
