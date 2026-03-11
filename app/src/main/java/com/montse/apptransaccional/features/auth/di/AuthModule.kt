package com.montse.apptransaccional.features.auth.di

import android.content.Context
import com.montse.apptransaccional.features.auth.data.repositories.BiometricAuthRepositoryImpl
import com.montse.apptransaccional.core.network.RestaurantApi
import com.montse.apptransaccional.core.session.SessionManager
import com.montse.apptransaccional.features.auth.data.repositories.AuthRepositoryImpl
import com.montse.apptransaccional.features.auth.domain.repositories.AuthRepository
import com.montse.apptransaccional.features.auth.domain.repositories.BiometricAuthRepository
import com.montse.apptransaccional.features.auth.domain.usecases.GetBiometricCredentialsUseCase
import com.montse.apptransaccional.features.auth.domain.usecases.IsBiometricLoginAvailableUseCase
import com.montse.apptransaccional.features.auth.domain.usecases.LoginUseCase
import com.montse.apptransaccional.features.auth.domain.usecases.RegisterUseCase
import com.montse.apptransaccional.features.auth.domain.usecases.SaveBiometricCredentialsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    @Singleton
    fun provideBiometricAuthRepository(
        @ApplicationContext context: Context
    ): BiometricAuthRepository {
        return BiometricAuthRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideSaveBiometricCredentialsUseCase(
        repository: BiometricAuthRepository
    ): SaveBiometricCredentialsUseCase {
        return SaveBiometricCredentialsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetBiometricCredentialsUseCase(
        repository: BiometricAuthRepository
    ): GetBiometricCredentialsUseCase {
        return GetBiometricCredentialsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideIsBiometricLoginAvailableUseCase(
        repository: BiometricAuthRepository
    ): IsBiometricLoginAvailableUseCase {
        return IsBiometricLoginAvailableUseCase(repository)
    }
}
