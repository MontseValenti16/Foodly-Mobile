package com.montse.apptransaccional.features.auth.domain.repositories

import com.montse.apptransaccional.features.auth.data.datasources.remote.AuthResponse

interface AuthRepository {
    suspend fun register(username: String, email: String, pass: String): AuthResponse
    suspend fun login(username: String, pass: String): AuthResponse
}
