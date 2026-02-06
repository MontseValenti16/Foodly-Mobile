package com.montse.apptransaccional.core.network

import com.montse.apptransaccional.features.auth.data.datasources.remote.AuthResponse
import com.montse.apptransaccional.features.auth.data.datasources.remote.LoginRequest
import com.montse.apptransaccional.features.auth.data.datasources.remote.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface RestaurantApi {
    @POST("users/auth/login")
    suspend fun login(@Body request: LoginRequest): AuthResponse

    @POST("users/auth/register")
    suspend fun register(@Body request: RegisterRequest): AuthResponse
}

