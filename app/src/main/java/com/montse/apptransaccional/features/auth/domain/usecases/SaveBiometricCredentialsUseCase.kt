package com.montse.apptransaccional.features.auth.domain.usecases

import com.montse.apptransaccional.features.auth.domain.repositories.BiometricAuthRepository
import javax.inject.Inject

class SaveBiometricCredentialsUseCase @Inject constructor(
    private val repository: BiometricAuthRepository
) {
    operator fun invoke(username: String, pass: String) = repository.saveCredentials(username, pass)
}
