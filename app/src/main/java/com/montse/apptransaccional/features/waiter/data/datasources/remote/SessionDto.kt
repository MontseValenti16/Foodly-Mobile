package com.montse.apptransaccional.features.waiter.data.datasources.remote

import com.google.gson.annotations.SerializedName

data class SessionDto(
    val id: Int,
    @SerializedName("table_id") val tableId: Int,
    @SerializedName("waiter_id") val waiterId: Int,
    @SerializedName("opened_at") val openedAt: String?,
    @SerializedName("closed_at") val closedAt: String?,
    val status: String,
    @SerializedName("table_number") val tableNumber: Int?,
    @SerializedName("waiter_name") val waiterName: String?
)
