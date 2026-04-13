package com.montse.apptransaccional.features.waiter.data.datasources.remote

import com.google.gson.annotations.SerializedName

data class OrderDto(
    val id: Int,
    @SerializedName("session_id") val sessionId: Int,
    @SerializedName("waiter_id") val waiterId: Int,
    @SerializedName("created_at") val createdAt: String?,
    val status: String,
    @SerializedName("table_id") val tableId: Int?,
    val items: List<OrderItemDto>?
)

data class OrderItemDto(
    val id: Int,
    @SerializedName("order_id") val orderId: Int,
    @SerializedName("product_id") val productId: Int,
    @SerializedName("area_id") val areaId: Int,
    @SerializedName("product_name") val productName: String,
    @SerializedName("unit_price") val unitPrice: String,
    val quantity: Int,
    val notes: String?,
    val status: String,
    @SerializedName("sent_to_area_at") val sentToAreaAt: String?,
    @SerializedName("ready_at") val readyAt: String?,
    @SerializedName("delivered_at") val deliveredAt: String?
)
