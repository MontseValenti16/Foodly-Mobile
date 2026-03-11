package com.montse.apptransaccional.features.tables.data.datasources.remote

import com.google.gson.annotations.SerializedName

data class CreateTableRequest(
    val number: Int,
    val capacity: Int,
    @SerializedName("is_active") val isActive: Boolean = true
)
