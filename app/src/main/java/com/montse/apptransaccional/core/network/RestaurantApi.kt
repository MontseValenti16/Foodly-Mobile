package com.montse.apptransaccional.core.network

import com.montse.apptransaccional.features.auth.data.datasources.remote.AuthResponse
import com.montse.apptransaccional.features.auth.data.datasources.remote.LoginRequest
import com.montse.apptransaccional.features.auth.data.datasources.remote.RegisterRequest
import com.montse.apptransaccional.features.auth.data.datasources.remote.UserData
import com.montse.apptransaccional.features.dashboard.data.datasources.remote.AreaDto
import com.montse.apptransaccional.features.dashboard.data.datasources.remote.CategoryDto
import com.montse.apptransaccional.features.dashboard.data.datasources.remote.DishDto
import com.montse.apptransaccional.features.dashboard.data.datasources.remote.DishResponse
import com.montse.apptransaccional.features.dashboard.data.datasources.remote.UpdateDishRequest
import com.montse.apptransaccional.features.tables.data.datasources.remote.CreateTableRequest
import com.montse.apptransaccional.features.tables.data.datasources.remote.TableDto
import com.montse.apptransaccional.features.tables.data.datasources.remote.TablesResponse
import com.montse.apptransaccional.features.users.data.datasources.remote.CreateEmployeeRequest
import com.montse.apptransaccional.features.users.data.datasources.remote.EmployeesResponse
import com.montse.apptransaccional.features.users.data.datasources.remote.RolesResponse
import com.montse.apptransaccional.features.users.data.datasources.remote.UserResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface RestaurantApi {
    @POST("users/auth/login")
    suspend fun login(@Body request: LoginRequest): AuthResponse

    @POST("users/auth/register")
    suspend fun register(@Body request: RegisterRequest): AuthResponse

    // User endpoints
    @GET("users")
    suspend fun getUsers(): EmployeesResponse

    @GET("users/{id}")
    suspend fun getUserById(@Path("id") id: Int): UserResponse

    @POST("users/auth/register")
    suspend fun createUser(@Body user: CreateEmployeeRequest): AuthResponse

    @PUT("users/{id}")
    suspend fun updateUser(@Path("id") id: Int, @Body user: UserData): UserData

    @DELETE("users/{id}")
    suspend fun deleteUser(@Path("id") id: Int): Map<String, Any>

    // Dish endpoints
    @GET("dishes")
    suspend fun getDishes(): List<DishDto>

    @GET("dishes/{id}")
    suspend fun getDishById(@Path("id") id: Int): DishResponse

    @Multipart
    @POST("dishes")
    suspend fun createDish(
        @Part image: MultipartBody.Part?,
        @Part("name") name: RequestBody,
        @Part("description") description: RequestBody?,
        @Part("price") price: RequestBody,
        @Part("category_id") categoryId: RequestBody,
        @Part("area_id") areaId: RequestBody,
        @Part("disponible") disponible: RequestBody
    ): DishDto

    @PUT("dishes/{id}")
    suspend fun updateDish(
        @Path("id") id: Int,
        @Body request: UpdateDishRequest
    ): Map<String, Any>

    @DELETE("dishes/{id}")
    suspend fun deleteDish(@Path("id") id: Int): Map<String, Any>

    // Table endpoints
    @GET("tables")
    suspend fun getTables(): TablesResponse

    @POST("tables")
    suspend fun createTable(@Body request: CreateTableRequest): TableDto

    @DELETE("tables/{id}")
    suspend fun deleteTable(@Path("id") id: Int): Map<String, Any>

    // Category endpoints
    @GET("categories")
    suspend fun getCategories(): List<CategoryDto>

    // Area endpoints
    @GET("areas")
    suspend fun getAreas(): List<AreaDto>

    // Role endpoints
    @GET("roles")
    suspend fun getRoles(): RolesResponse
}
