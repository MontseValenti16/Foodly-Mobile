package com.montse.apptransaccional.features.waiter.data.repositories

import com.montse.apptransaccional.core.network.RestaurantApi
import com.montse.apptransaccional.features.dashboard.domain.models.Category
import com.montse.apptransaccional.features.dashboard.domain.models.Dish
import com.montse.apptransaccional.features.waiter.data.datasources.remote.CreateOrderItemRequest
import com.montse.apptransaccional.features.waiter.data.datasources.remote.CreateOrderRequest
import com.montse.apptransaccional.features.waiter.data.datasources.remote.CreateSessionRequest
import com.montse.apptransaccional.features.waiter.data.datasources.remote.CreateTicketRequest
import com.montse.apptransaccional.features.waiter.data.datasources.remote.OrderDto
import com.montse.apptransaccional.features.waiter.data.datasources.remote.TicketDto
import com.montse.apptransaccional.features.waiter.domain.models.CartItem
import com.montse.apptransaccional.features.waiter.domain.models.TableStatus
import com.montse.apptransaccional.features.waiter.domain.models.WaiterTable
import com.montse.apptransaccional.features.waiter.domain.repositories.WaiterRepository

class WaiterRepositoryImpl(
    private val api: RestaurantApi
) : WaiterRepository {

    override suspend fun getTablesWithStatus(): List<WaiterTable> {
        val tables = api.getTables().tables
        val openSessions = api.getSessions(status = "open")
        val sessionByTableId = openSessions.associateBy { it.tableId }

        return tables
            .filter { it.isActive == 1 }
            .map { table ->
                val session = sessionByTableId[table.id]
                WaiterTable(
                    id = table.id,
                    number = table.number,
                    capacity = table.capacity,
                    status = if (session != null) TableStatus.OCUPADA else TableStatus.LIBRE,
                    sessionId = session?.id,
                    waiterName = session?.waiterName
                )
            }
            .sortedBy { it.number }
    }

    override suspend fun openSession(tableId: Int): Int {
        val session = api.createSession(CreateSessionRequest(tableId = tableId))
        return session.id
    }

    override suspend fun getProducts(): List<Dish> {
        return api.getDishes()
            .filter { (it.isAvailable ?: 0) == 1 && (it.isActive ?: 0) == 1 }
            .map { dto ->
                Dish(
                    id = dto.id ?: 0,
                    nombre = dto.name ?: "",
                    descripcion = dto.description,
                    precio = dto.price?.toDoubleOrNull() ?: 0.0,
                    categoria = dto.categoryName,
                    disponible = (dto.isAvailable ?: 0) == 1,
                    imageUrl = dto.imageUrl,
                    areaId = dto.areaId,
                    categoryId = dto.categoryId
                )
            }
    }

    override suspend fun getCategories(): List<Category> {
        return api.getCategories()
            .filter { it.isActive == 1 }
            .map { Category(id = it.id, name = it.name, isActive = true) }
    }

    override suspend fun getSessionOrders(sessionId: Int): List<OrderDto> {
        return api.getOrdersBySession(sessionId)
    }

    override suspend fun sendOrder(sessionId: Int, items: List<CartItem>) {
        val request = CreateOrderRequest(
            sessionId = sessionId,
            items = items.map {
                CreateOrderItemRequest(
                    productId = it.productId,
                    quantity = it.quantity,
                    notes = it.notes
                )
            }
        )
        api.createOrder(request)
    }

    override suspend fun updateItemStatus(itemId: Int, status: String) {
        api.updateItemStatus(itemId, mapOf("status" to status))
    }

    override suspend fun closeAndPrintTicket(
        sessionId: Int,
        paymentMethod: String,
        tip: Double,
        discount: Double,
        notes: String?
    ): TicketDto {
        return api.createTicket(
            CreateTicketRequest(
                sessionId = sessionId,
                paymentMethod = paymentMethod,
                tip = tip,
                discount = discount,
                notes = notes
            )
        )
    }
}
