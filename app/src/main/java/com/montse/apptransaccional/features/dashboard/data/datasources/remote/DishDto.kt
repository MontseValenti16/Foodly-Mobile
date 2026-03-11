package com.montse.apptransaccional.features.dashboard.data.datasources.remote

import com.google.gson.annotations.SerializedName

data class DishDto(
    @SerializedName("id") val id: Int?,
    @SerializedName("area_id") val areaId: Int?,
    @SerializedName("area_name") val areaName: String?,
    @SerializedName("category_id") val categoryId: Int?,
    @SerializedName("category_name") val categoryName: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("price") val price: String?,
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("is_available") val isAvailable: Int?,
    @SerializedName("is_active") val isActive: Int?,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("updated_at") val updatedAt: String?
)
