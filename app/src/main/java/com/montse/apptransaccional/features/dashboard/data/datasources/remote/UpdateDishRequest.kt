package com.montse.apptransaccional.features.dashboard.data.datasources.remote

import com.google.gson.annotations.SerializedName

data class UpdateDishRequest(
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String?,
    @SerializedName("price") val price: Double,
    @SerializedName("area_id") val areaId: Int,
    @SerializedName("category_id") val categoryId: Int,
    @SerializedName("is_available") val isAvailable: Boolean,
    @SerializedName("is_active") val isActive: Boolean
)
