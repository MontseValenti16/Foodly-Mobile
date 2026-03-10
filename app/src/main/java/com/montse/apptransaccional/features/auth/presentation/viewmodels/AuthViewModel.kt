package com.montse.apptransaccional.features.auth.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.montse.apptransaccional.features.auth.domain.usecases.LoginUseCase
import com.montse.apptransaccional.features.auth.domain.usecases.RegisterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _authState = MutableStateFlow(AuthState())
    val authState : StateFlow<AuthState> = _authState.asStateFlow()
    private var nameTouched = false
    private var emailTouched = false
    private var passwordTouched = false
    private var attemptedSubmit = false

    fun togglePasswordVisibility() {
        _authState.value = _authState.value.copy(
            isPasswordVisible = !_authState.value.isPasswordVisible
        )
    }

    fun onNameChange(value: String) {
        if (!nameTouched) nameTouched = true
        _authState.value = applyValidation(_authState.value.copy(name = value))
    }

    fun onEmailChange(value: String) {
        if (!emailTouched) emailTouched = true
        _authState.value = applyValidation(_authState.value.copy(email = value))
    }

    fun onPasswordChange(value: String) {
        if (!passwordTouched) passwordTouched = true
        _authState.value = applyValidation(_authState.value.copy(password = value))
    }

    private fun applyValidation(baseState: AuthState): AuthState {
        val nameValue = baseState.name.trim()
        val emailValue = baseState.email.trim()
        val passwordValue = baseState.password
        val emailRegex = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$".toRegex()

        val nameError = when {
            nameValue.isEmpty() -> "El nombre es obligatorio"
            nameValue.length < 2 -> "El nombre debe tener al menos 2 caracteres"
            else -> null
        }
        val emailError = when {
            emailValue.isEmpty() -> "El correo es obligatorio"
            !emailRegex.matches(emailValue) -> "El correo no es valido"
            else -> null
        }
        val passwordError = when {
            passwordValue.isEmpty() -> "La contrasena es obligatoria"
            passwordValue.length < 6 -> "La contrasena debe tener al menos 6 caracteres"
            else -> null
        }

        val shouldShowNameError = (nameTouched || attemptedSubmit) && nameError != null
        val shouldShowEmailError = (emailTouched || attemptedSubmit) && emailError != null
        val shouldShowPasswordError = (passwordTouched || attemptedSubmit) && passwordError != null
        val isLoginValid = emailError == null && passwordError == null
        val isRegisterValid = nameError == null && emailError == null && passwordError == null

        return baseState.copy(
            nameError = nameError,
            emailError = emailError,
            passwordError = passwordError,
            shouldShowNameError = shouldShowNameError,
            shouldShowEmailError = shouldShowEmailError,
            shouldShowPasswordError = shouldShowPasswordError,
            isLoginValid = isLoginValid,
            isRegisterValid = isRegisterValid
        )
    }

    private fun markSubmitAttempted() {
        attemptedSubmit = true
        _authState.value = applyValidation(_authState.value)
    }

    fun login(onSuccess: () -> Unit) {
        viewModelScope.launch {
            markSubmitAttempted()
            if (!_authState.value.isLoginValid) return@launch

            _authState.value = _authState.value.copy(isLoading = true, error = null)
            try {
                loginUseCase(_authState.value.email, _authState.value.password)

                _authState.value = _authState.value.copy(isLoading = false)
                onSuccess()
            } catch (e: Exception) {
                _authState.value = _authState.value.copy(
                    isLoading = false,
                    error = "Error al iniciar sesión: ${e.message}"
                )
            }
        }
    }

    fun register(onSuccess: () -> Unit) {
        viewModelScope.launch {
            markSubmitAttempted()
            if (!_authState.value.isRegisterValid) return@launch

            _authState.value = _authState.value.copy(isLoading = true, error = null)
            try {
                registerUseCase(
                    _authState.value.name,
                    _authState.value.email,
                    _authState.value.password
                )

                _authState.value = _authState.value.copy(isLoading = false)
                onSuccess()
            } catch (e: Exception) {
                _authState.value = _authState.value.copy(
                    isLoading = false,
                    error = "Error al registrarse: ${e.message}"
                )
            }
        }
    }
}

