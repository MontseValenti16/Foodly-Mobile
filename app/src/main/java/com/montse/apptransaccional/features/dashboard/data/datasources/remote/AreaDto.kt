package com.montse.apptransaccional.features.dashboard.data.datasources.remote

import com.google.gson.annotations.SerializedName

data class AreaDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("icon") val icon: String?,
    @SerializedName("color") val color: String?,
    @SerializedName("is_active") val isActive: Int
)
