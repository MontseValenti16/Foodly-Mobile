package com.montse.apptransaccional.core.messaging

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

sealed class OrderEvent {
    data class NewOrder(val areaId: String?, val tableNumber: String?) : OrderEvent()
    data class StatusUpdate(val itemId: String?, val status: String?, val tableNumber: String?) : OrderEvent()
}

object OrderEventBus {
    private val _events = MutableSharedFlow<OrderEvent>(extraBufferCapacity = 10)
    val events: SharedFlow<OrderEvent> = _events.asSharedFlow()

    fun emit(event: OrderEvent) {
        _events.tryEmit(event)
    }
}
