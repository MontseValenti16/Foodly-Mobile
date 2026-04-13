package com.montse.apptransaccional.features.waiter.data.datasources.remote

import com.google.gson.annotations.SerializedName

data class CreateOrderRequest(
    @SerializedName("session_id") val sessionId: Int,
    val items: List<CreateOrderItemRequest>
)

data class CreateOrderItemRequest(
    @SerializedName("product_id") val productId: Int,
    val quantity: Int,
    val notes: String? = null
)
