package com.montse.apptransaccional.features.auth.domain.usecases

import com.montse.apptransaccional.features.auth.domain.repositories.AuthRepository

class RegisterUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(username: String, email: String, pass: String) = repository.register(username, email, pass)
}