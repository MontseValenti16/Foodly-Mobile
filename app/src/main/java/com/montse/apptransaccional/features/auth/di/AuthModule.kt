package com.montse.apptransaccional.features.auth.di

import com.montse.apptransaccional.core.network.RestaurantApi
import com.montse.apptransaccional.core.session.SessionManager
import com.montse.apptransaccional.features.auth.data.repositories.AuthRepositoryImpl
import com.montse.apptransaccional.features.auth.data.repositories.BiometricAuthRepositoryImpl
import com.montse.apptransaccional.features.auth.domain.repositories.AuthRepository
import com.montse.apptransaccional.features.auth.domain.repositories.BiometricAuthRepository
import com.montse.apptransaccional.features.auth.domain.usecases.LoginUseCase
import com.montse.apptransaccional.features.auth.domain.usecases.RegisterUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindBiometricAuthRepository(
        impl: BiometricAuthRepositoryImpl
    ): BiometricAuthRepository

    companion object {
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
}
