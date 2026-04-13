package com.montse.apptransaccional.features.waiter.domain.models

data class CartItem(
    val productId: Int,
    val name: String,
    val price: Double,
    val quantity: Int,
    val notes: String? = null
) {
    val subtotal: Double get() = price * quantity
}
