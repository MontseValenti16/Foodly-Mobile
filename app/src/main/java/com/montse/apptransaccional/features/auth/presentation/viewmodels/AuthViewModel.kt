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

    // Estado único que maneja los datos de la pantalla
    var state by mutableStateOf(AuthState())

    // --- FUNCIÓN LOGIN ---
    fun login(onSuccess: () -> Unit) {
        viewModelScope.launch {
            state = state.copy(isLoading = true, error = null)
            try {
                // Llamada al caso de uso (API)
                loginUseCase(state.email, state.password)

                state = state.copy(isLoading = false)
                onSuccess() // Navegar si todo sale bien
            } catch (e: Exception) {
                state = state.copy(
                    isLoading = false,
                    error = "Error al iniciar sesión: ${e.message}"
                )
            }
        }
    }

    // --- FUNCIÓN REGISTRO (LA QUE FALTABA) ---
    fun register(onSuccess: () -> Unit) {
        viewModelScope.launch {
            state = state.copy(isLoading = true, error = null)
            try {
                // Llamada al caso de uso (API) enviando nombre, email y pass
                registerUseCase(state.name, state.email, state.password)

                state = state.copy(isLoading = false)
                onSuccess() // Navegar si todo sale bien
            } catch (e: Exception) {
                state = state.copy(
                    isLoading = false,
                    error = "Error al registrarse: ${e.message}"
                )
            }
        }
    }
}

// Modelo del estado de la UI
data class AuthState(
    var email: String = "",
    var password: String = "",
    var name: String = "", // Campo necesario para registro
    var isLoading: Boolean = false,
    var error: String? = null
)