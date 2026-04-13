package com.montse.apptransaccional.features.auth.domain.repositories

import com.montse.apptransaccional.features.auth.domain.models.BiometricCredentials

interface BiometricAuthRepository {
    fun isBiometricAvailable(): Boolean
    fun hasSavedCredentials(): Boolean
    fun getSavedCredentials(): BiometricCredentials?
    fun saveCredentials(username: String, password: String)
    fun clearSavedCredentials()
}
