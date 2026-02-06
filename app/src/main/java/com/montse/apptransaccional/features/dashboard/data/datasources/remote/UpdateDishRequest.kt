package com.montse.apptransaccional.features.dashboard.data.datasources.remote

data class UpdateDishRequest(
    val nombre: String,
    val descripcion: String?,
    val precio: Double,
    val categoria: String?,
    val disponible: Int
)
