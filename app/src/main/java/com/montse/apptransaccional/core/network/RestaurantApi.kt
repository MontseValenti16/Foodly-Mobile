package com.montse.apptransaccional.core.network

import com.montse.apptransaccional.features.auth.data.datasources.remote.AuthResponse
import com.montse.apptransaccional.features.auth.data.datasources.remote.LoginRequest
import com.montse.apptransaccional.features.auth.data.datasources.remote.RegisterRequest
import com.montse.apptransaccional.features.auth.data.datasources.remote.UserData
import com.montse.apptransaccional.features.dashboard.data.datasources.remote.CreateDishRequest
import com.montse.apptransaccional.features.dashboard.data.datasources.remote.DishDto
import com.montse.apptransaccional.features.dashboard.data.datasources.remote.DishResponse
import com.montse.apptransaccional.features.dashboard.data.datasources.remote.UpdateDishRequest
import com.montse.apptransaccional.features.users.data.datasources.remote.UserResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface RestaurantApi {
    @POST("users/auth/login")
    suspend fun login(@Body request: LoginRequest): AuthResponse

    @POST("users/auth/register")
    suspend fun register(@Body request: RegisterRequest): AuthResponse

    // User endpoints
    @GET("users")
    suspend fun getUsers(): List<UserData>

    @GET("users/{id}")
    suspend fun getUserById(@Path("id") id: Int): UserResponse

    @POST("users")
    suspend fun createUser(@Body user: UserData): UserData

    @PUT("users/{id}")
    suspend fun updateUser(@Path("id") id: Int, @Body user: UserData): UserData

    @DELETE("users/{id}")
    suspend fun deleteUser(@Path("id") id: Int): Map<String, Any>

    // Dish endpoints
    @GET("dishes")
    suspend fun getDishes(): List<DishDto>

    @GET("dishes/{id}")
    suspend fun getDishById(@Path("id") id: Int): DishResponse

    @POST("dishes")
    suspend fun createDish(@Body request: CreateDishRequest): DishDto

    @PUT("dishes/{id}")
    suspend fun updateDish(
        @Path("id") id: Int,
        @Body request: UpdateDishRequest
    ): Map<String, Any>

    @DELETE("dishes/{id}")
    suspend fun deleteDish(@Path("id") id: Int): Map<String, Any>
}
