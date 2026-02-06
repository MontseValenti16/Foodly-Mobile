package com.montse.apptransaccional.features.auth.domain.usecases

import com.montse.apptransaccional.features.auth.domain.repositories.AuthRepository

class LoginUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, pass: String) = repository.login(email, pass)
}