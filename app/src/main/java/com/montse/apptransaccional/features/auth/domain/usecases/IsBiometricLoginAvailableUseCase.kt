package com.montse.apptransaccional.features.auth.domain.usecases

import com.montse.apptransaccional.features.auth.domain.repositories.BiometricAuthRepository

class IsBiometricLoginAvailableUseCase(
    private val repository: BiometricAuthRepository
) {
    operator fun invoke(): Boolean {
        return repository.isBiometricAvailable() && repository.hasSavedCredentials()
    }
}
