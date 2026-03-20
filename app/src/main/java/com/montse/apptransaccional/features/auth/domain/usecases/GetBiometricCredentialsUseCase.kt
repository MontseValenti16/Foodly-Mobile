package com.montse.apptransaccional.features.auth.domain.usecases

import com.montse.apptransaccional.features.auth.domain.repositories.BiometricAuthRepository
import javax.inject.Inject

class GetBiometricCredentialsUseCase @Inject constructor(
    private val repository: BiometricAuthRepository
) {
    operator fun invoke() = repository.getSavedCredentials()
}
