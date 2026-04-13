package com.montse.apptransaccional.features.waiter.presentation.state

import com.montse.apptransaccional.features.dashboard.domain.models.Category
import com.montse.apptransaccional.features.dashboard.domain.models.Dish
import com.montse.apptransaccional.features.waiter.data.datasources.remote.OrderDto
import com.montse.apptransaccional.features.waiter.data.datasources.remote.OrderItemDto
import com.montse.apptransaccional.features.waiter.data.datasources.remote.TicketDto
import com.montse.apptransaccional.features.waiter.domain.models.CartItem

data class WaiterSessionState(
    val sessionId: Int = 0,
    val tableNumber: Int = 0,
    val isLoading: Boolean = false,
    val products: List<Dish> = emptyList(),
    val categories: List<Category> = emptyList(),
    val selectedCategory: String = "Todo",
    val orders: List<OrderDto> = emptyList(),
    val cart: List<CartItem> = emptyList(),
    val isSendingOrder: Boolean = false,
    val orderSentSuccess: Boolean = false,
    val error: String? = null,
    // Orders tab
    val showOrdersPanel: Boolean = false,
    // Close / Ticket
    val showCloseDialog: Boolean = false,
    val paymentMethod: String = "efectivo",
    val tip: String = "",
    val discount: String = "",
    val isClosingSession: Boolean = false,
    val ticket: TicketDto? = null,
    val showTicket: Boolean = false,
    val sessionClosed: Boolean = false
) {
    val filteredProducts: List<Dish>
        get() = if (selectedCategory == "Todo") products
                else products.filter { it.categoria == selectedCategory }

    val cartTotal: Double
        get() = cart.sumOf { it.subtotal }

    val cartItemCount: Int
        get() = cart.sumOf { it.quantity }

    val allItems: List<OrderItemDto>
        get() = orders.flatMap { it.items ?: emptyList() }

    val allDelivered: Boolean
        get() = allItems.isNotEmpty() && allItems.all { it.status == "delivered" || it.status == "cancelled" }

    val hasOrders: Boolean
        get() = orders.isNotEmpty()
}
