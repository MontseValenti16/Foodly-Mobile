package com.montse.apptransaccional.features.auth.presentation.viewmodels

data class AuthState(
    var username: String = "",
    var email: String = "",
    var password: String = "",
    var name: String = "",
    val isPasswordVisible: Boolean = false,
    var isLoading: Boolean = false,
    var error: String? = null,
    val nameError: String? = null,
    val usernameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val biometricError: String? = null,
    val shouldShowNameError: Boolean = false,
    val shouldShowUsernameError: Boolean = false,
    val shouldShowEmailError: Boolean = false,
    val shouldShowPasswordError: Boolean = false,
    val isLoginValid: Boolean = false,
    val isRegisterValid: Boolean = false,
    val isBiometricLoginAvailable: Boolean = false
)
