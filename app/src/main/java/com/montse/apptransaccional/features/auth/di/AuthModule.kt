package com.montse.apptransaccional.features.auth.di

import com.montse.apptransaccional.core.di.AppContainer
import com.montse.apptransaccional.features.auth.data.repositories.AuthRepositoryImpl
import com.montse.apptransaccional.features.auth.domain.usecases.LoginUseCase
import com.montse.apptransaccional.features.auth.domain.usecases.RegisterUseCase
import com.montse.apptransaccional.features.auth.presentation.viewmodels.AuthViewModelFactory

class AuthModule(appContainer: AppContainer) {
    private val repository = AuthRepositoryImpl(
        appContainer.restaurantApi,
        appContainer.sessionManager
    )
    private val loginUseCase = LoginUseCase(repository)
    private val registerUseCase = RegisterUseCase(repository)

    fun provideAuthViewModelFactory(): AuthViewModelFactory {
        return AuthViewModelFactory(loginUseCase, registerUseCase)
    }
}