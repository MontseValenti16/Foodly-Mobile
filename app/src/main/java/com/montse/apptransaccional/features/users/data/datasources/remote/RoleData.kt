package com.montse.apptransaccional.features.users.data.datasources.remote

import com.google.gson.annotations.SerializedName

data class RoleData(
    val id: Int,
    val name: String,
    val description: String,
    @SerializedName("created_at")
    val createdAt: String
)
