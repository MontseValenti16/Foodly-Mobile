package com.montse.apptransaccional.features.auth.data.repositories

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.montse.apptransaccional.features.auth.domain.models.BiometricCredentials
import com.montse.apptransaccional.features.auth.domain.repositories.BiometricAuthRepository

class BiometricAuthRepositoryImpl(
    context: Context
) : BiometricAuthRepository {

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val securePrefs = EncryptedSharedPreferences.create(
        context,
        PREFS_NAME,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    private val biometricManager = BiometricManager.from(context)

    override fun isBiometricAvailable(): Boolean {
        val result = biometricManager.canAuthenticate(
            BiometricManager.Authenticators.BIOMETRIC_STRONG or
                BiometricManager.Authenticators.BIOMETRIC_WEAK
        )
        return result == BiometricManager.BIOMETRIC_SUCCESS
    }

    override fun hasSavedCredentials(): Boolean {
        val username = securePrefs.getString(KEY_USERNAME, null)
        val password = securePrefs.getString(KEY_PASSWORD, null)
        return !username.isNullOrBlank() && !password.isNullOrBlank()
    }

    override fun getSavedCredentials(): BiometricCredentials? {
        val username = securePrefs.getString(KEY_USERNAME, null)
        val password = securePrefs.getString(KEY_PASSWORD, null)
        if (username.isNullOrBlank() || password.isNullOrBlank()) return null
        return BiometricCredentials(username = username, password = password)
    }

    override fun saveCredentials(username: String, password: String) {
        securePrefs.edit()
            .putString(KEY_USERNAME, username.trim())
            .putString(KEY_PASSWORD, password)
            .apply()
    }

    override fun clearSavedCredentials() {
        securePrefs.edit()
            .remove(KEY_USERNAME)
            .remove(KEY_PASSWORD)
            .apply()
    }

    private companion object {
        const val PREFS_NAME = "biometric_auth_prefs"
        const val KEY_USERNAME = "biometric_username"
        const val KEY_PASSWORD = "biometric_password"
    }
}
