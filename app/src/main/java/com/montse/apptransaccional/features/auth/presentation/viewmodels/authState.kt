package com.montse.apptransaccional.features.auth.presentation.viewmodels

data class AuthState(
    var email: String = "",
    var password: String = "",
    var name: String = "",
    val isPasswordVisible: Boolean = false,
    var isLoading: Boolean = false,
    var error: String? = null,
    val nameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val shouldShowNameError: Boolean = false,
    val shouldShowEmailError: Boolean = false,
    val shouldShowPasswordError: Boolean = false,
    val isLoginValid: Boolean = false,
    val isRegisterValid: Boolean = false
)