package com.montse.apptransaccional.features.auth.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.montse.apptransaccional.features.auth.domain.usecases.LoginUseCase
import com.montse.apptransaccional.features.auth.domain.usecases.RegisterUseCase

class AuthViewModelFactory(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthViewModel(loginUseCase, registerUseCase) as T
    }
}