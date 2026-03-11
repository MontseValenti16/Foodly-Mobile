package com.montse.apptransaccional.features.auth.domain.usecases

import com.montse.apptransaccional.features.auth.domain.repositories.BiometricAuthRepository

class SaveBiometricCredentialsUseCase(
    private val repository: BiometricAuthRepository
) {
    operator fun invoke(username: String, password: String) {
        repository.saveCredentials(username, password)
    }
}
