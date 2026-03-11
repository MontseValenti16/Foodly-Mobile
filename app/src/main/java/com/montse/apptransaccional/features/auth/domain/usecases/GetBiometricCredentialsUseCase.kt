package com.montse.apptransaccional.features.auth.domain.usecases

import com.montse.apptransaccional.features.auth.domain.repositories.BiometricAuthRepository

class GetBiometricCredentialsUseCase(
    private val repository: BiometricAuthRepository
) {
    operator fun invoke() = repository.getSavedCredentials()
}
