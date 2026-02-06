package com.montse.apptransaccional.features.auth.data.repositories

import com.montse.apptransaccional.core.network.RestaurantApi
import com.montse.apptransaccional.features.auth.data.datasources.remote.AuthResponse
import com.montse.apptransaccional.features.auth.data.datasources.remote.LoginRequest
import com.montse.apptransaccional.features.auth.data.datasources.remote.RegisterRequest
import com.montse.apptransaccional.features.auth.domain.repositories.AuthRepository

class AuthRepositoryImpl(private val api: RestaurantApi) : AuthRepository {
    override suspend fun login(email: String, pass: String): AuthResponse {
        return api.login(LoginRequest(email, pass))
    }
    override suspend fun register(username: String, email: String, pass: String): AuthResponse {
        return api.register(RegisterRequest(username, email, pass))
    }
}