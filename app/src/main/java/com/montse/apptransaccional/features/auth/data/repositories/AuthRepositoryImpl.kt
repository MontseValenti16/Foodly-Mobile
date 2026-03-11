package com.montse.apptransaccional.features.auth.data.repositories

import com.montse.apptransaccional.core.network.RestaurantApi
import com.montse.apptransaccional.features.auth.data.datasources.remote.AuthResponse
import com.montse.apptransaccional.features.auth.data.datasources.remote.LoginRequest
import com.montse.apptransaccional.features.auth.data.datasources.remote.RegisterRequest
import com.montse.apptransaccional.core.session.SessionManager
import com.montse.apptransaccional.features.auth.domain.repositories.AuthRepository

class AuthRepositoryImpl(
    private val api: RestaurantApi,
    private val sessionManager: SessionManager
) : AuthRepository {
    override suspend fun login(username: String, pass: String): AuthResponse {
        val response = api.login(LoginRequest(username, pass))
        sessionManager.saveToken(response.token)
        return response
    }
    override suspend fun register(username: String, email: String, pass: String): AuthResponse {
        return api.register(
            RegisterRequest(
                email = email,
                password = pass,
                username = username
            )
        )
    }
}