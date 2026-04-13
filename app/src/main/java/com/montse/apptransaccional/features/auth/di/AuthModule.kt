package com.montse.apptransaccional.features.auth.di

import com.montse.apptransaccional.core.network.RestaurantApi
import com.montse.apptransaccional.core.session.SessionManager
import com.montse.apptransaccional.features.auth.data.repositories.AuthRepositoryImpl
import com.montse.apptransaccional.features.auth.data.repositories.BiometricAuthRepositoryImpl
import com.montse.apptransaccional.features.auth.data.repositories.NotificationRepositoryImpl
import com.montse.apptransaccional.features.auth.domain.repositories.AuthRepository
import com.montse.apptransaccional.features.auth.domain.repositories.BiometricAuthRepository
import com.montse.apptransaccional.features.auth.domain.repositories.NotificationRepository
import com.montse.apptransaccional.features.auth.domain.usecases.LoginUseCase
import com.montse.apptransaccional.features.auth.domain.usecases.RegisterUseCase
import com.montse.apptransaccional.features.auth.domain.usecases.UpdateFcmTokenUseCase
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

    @Binds
    @Singleton
    abstract fun bindNotificationRepository(
        impl: NotificationRepositoryImpl
    ): NotificationRepository

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

        @Provides
        @Singleton
        fun provideUpdateFcmTokenUseCase(repository: NotificationRepository): UpdateFcmTokenUseCase {
            return UpdateFcmTokenUseCase(repository)
        }
    }
}
