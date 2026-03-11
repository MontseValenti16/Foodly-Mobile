package com.montse.apptransaccional.features.users.domain.models

data class User(
    val id: Int,
    val name: String,
    val username: String,
    val email: String? = null,
    val isActive: Boolean,
    val roleId: Int? = null,
    val role: String,
    val areaId: Int? = null,
    val areaName: String? = null,
    val areaIcon: String? = null,
    val areaColor: String? = null
) {
    val roleLabel: String
        get() = when (role.lowercase()) {
            "mesero" -> "Waiter"
            "barra" -> "Bar"
            "cocina" -> "Kitchen"
            "admin" -> "Admin"
            else -> role.replaceFirstChar { it.uppercase() }
        }
}
