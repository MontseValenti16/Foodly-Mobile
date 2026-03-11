package com.montse.apptransaccional.features.dashboard.domain.models

data class Dish(
    val id: Int,
    val nombre: String,
    val descripcion: String?,
    val precio: Double,
    val categoria: String?,
    val disponible: Boolean,
    val imageUrl: String? = null,
    val areaId: Int? = null,
    val categoryId: Int? = null
)
