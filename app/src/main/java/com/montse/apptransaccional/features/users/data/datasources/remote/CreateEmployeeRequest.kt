package com.montse.apptransaccional.features.users.data.datasources.remote

import com.google.gson.annotations.SerializedName

data class CreateEmployeeRequest(
    val name: String,
    val username: String,
    val password: String,
    @SerializedName("role_id") val roleId: Int
)
