package com.montse.apptransaccional.features.auth.data.datasources.remote

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    val token: String,
    val message: String? = null,
    @SerializedName("userID") val userId: Int,
    @SerializedName("userRole") val userRole: String? = null,
    val user: UserData? = null
)

data class UserData(
    val id: Int,
    val name: String,
    val username: String,
    @SerializedName("is_active") val isActive: Int,
    @SerializedName("created_at") val createdAt: String? = null,
    @SerializedName("updated_at") val updatedAt: String? = null,
    @SerializedName("role_id") val roleId: Int? = null,
    val role: String,
    @SerializedName("area_id") val areaId: Int? = null,
    @SerializedName("area_name") val areaName: String? = null,
    @SerializedName("area_icon") val areaIcon: String? = null,
    @SerializedName("area_color") val areaColor: String? = null
)
