package com.montse.apptransaccional.features.waiter.data.datasources.remote

import com.google.gson.annotations.SerializedName

data class CreateSessionRequest(
    @SerializedName("table_id") val tableId: Int
)
