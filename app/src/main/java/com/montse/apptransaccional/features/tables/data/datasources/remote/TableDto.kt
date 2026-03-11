package com.montse.apptransaccional.features.tables.data.datasources.remote

import com.google.gson.annotations.SerializedName

data class TableDto(
    val id: Int,
    val number: Int,
    val capacity: Int,
    @SerializedName("is_active") val isActive: Int,
    @SerializedName("created_at") val createdAt: String?
)
