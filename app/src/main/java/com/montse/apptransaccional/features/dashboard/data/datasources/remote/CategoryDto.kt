package com.montse.apptransaccional.features.dashboard.data.datasources.remote

import com.google.gson.annotations.SerializedName

data class CategoryDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("sort_order") val sortOrder: Int,
    @SerializedName("is_active") val isActive: Int
)
