package com.montse.apptransaccional.features.auth.presentation.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.montse.apptransaccional.features.auth.domain.usecases.LoginUseCase
import com.montse.apptransaccional.features.auth.domain.usecases.RegisterUseCase
import kotlinx.coroutines.launch

class AuthViewModel(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    var state by mutableStateOf(AuthState())

    fun login(onSuccess: () -> Unit) {
        viewModelScope.launch {
            state = state.copy(isLoading = true, error = null)
            try {
                loginUseCase(state.email, state.password)

                state = state.copy(isLoading = false)
                onSuccess()
            } catch (e: Exception) {
                state = state.copy(
                    isLoading = false,
                    error = "Error al iniciar sesión: ${e.message}"
                )
            }
        }
    }

    fun register(onSuccess: () -> Unit) {
        viewModelScope.launch {
            state = state.copy(isLoading = true, error = null)
            try {
                registerUseCase(state.name, state.email, state.password)

                state = state.copy(isLoading = false)
                onSuccess()
            } catch (e: Exception) {
                state = state.copy(
                    isLoading = false,
                    error = "Error al registrarse: ${e.message}"
                )
            }
        }
    }
}

data class AuthState(
    var email: String = "",
    var password: String = "",
    var name: String = "",
    var isLoading: Boolean = false,
    var error: String? = null
)