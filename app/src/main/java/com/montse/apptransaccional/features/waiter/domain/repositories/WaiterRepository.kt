package com.montse.apptransaccional.features.waiter.domain.repositories

import com.montse.apptransaccional.features.dashboard.domain.models.Dish
import com.montse.apptransaccional.features.dashboard.domain.models.Category
import com.montse.apptransaccional.features.waiter.data.datasources.remote.OrderDto
import com.montse.apptransaccional.features.waiter.data.datasources.remote.TicketDto
import com.montse.apptransaccional.features.waiter.domain.models.CartItem
import com.montse.apptransaccional.features.waiter.domain.models.WaiterTable

interface WaiterRepository {
    suspend fun getTablesWithStatus(): List<WaiterTable>
    suspend fun openSession(tableId: Int): Int
    suspend fun getProducts(): List<Dish>
    suspend fun getCategories(): List<Category>
    suspend fun getSessionOrders(sessionId: Int): List<OrderDto>
    suspend fun sendOrder(sessionId: Int, items: List<CartItem>)
    suspend fun updateItemStatus(itemId: Int, status: String)
    suspend fun closeAndPrintTicket(sessionId: Int, paymentMethod: String, tip: Double, discount: Double, notes: String?): TicketDto
}
