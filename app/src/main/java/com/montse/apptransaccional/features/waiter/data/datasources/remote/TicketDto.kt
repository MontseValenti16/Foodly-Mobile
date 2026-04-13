package com.montse.apptransaccional.features.waiter.data.datasources.remote

import com.google.gson.annotations.SerializedName

data class TicketDto(
    val id: Int,
    @SerializedName("session_id") val sessionId: Int,
    @SerializedName("waiter_id") val waiterId: Int,
    val subtotal: String,
    val discount: String,
    val tip: String,
    val total: String,
    @SerializedName("payment_method") val paymentMethod: String,
    val notes: String?,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("table_number") val tableNumber: Int?,
    @SerializedName("waiter_name") val waiterName: String?,
    val items: List<TicketItemDto>?
)

data class TicketItemDto(
    val id: Int,
    @SerializedName("ticket_id") val ticketId: Int,
    @SerializedName("product_id") val productId: Int,
    @SerializedName("product_name") val productName: String,
    @SerializedName("unit_price") val unitPrice: String,
    val quantity: Int,
    val subtotal: String
)

data class CreateTicketRequest(
    @SerializedName("session_id") val sessionId: Int,
    @SerializedName("payment_method") val paymentMethod: String = "efectivo",
    val tip: Double = 0.0,
    val discount: Double = 0.0,
    val notes: String? = null
)
