package com.montse.apptransaccional.core.data.remote

import com.google.gson.annotations.SerializedName

data class AreaItemDto(
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
    @SerializedName("delivered_at") val deliveredAt: String?,
    @SerializedName("table_number") val tableNumber: Int?,
    @SerializedName("waiter_name") val waiterName: String?
)
