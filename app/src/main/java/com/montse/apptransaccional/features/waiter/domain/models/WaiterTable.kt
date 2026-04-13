package com.montse.apptransaccional.features.waiter.domain.models

data class WaiterTable(
    val id: Int,
    val number: Int,
    val capacity: Int,
    val status: TableStatus,
    val sessionId: Int?,
    val waiterName: String?
)

enum class TableStatus(val label: String, val key: String) {
    LIBRE("Libre", "libre"),
    OCUPADA("Ocupada", "ocupada");
}
