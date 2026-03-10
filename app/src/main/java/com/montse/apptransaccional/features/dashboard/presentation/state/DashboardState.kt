package com.montse.apptransaccional.features.dashboard.presentation.state

import com.montse.apptransaccional.features.dashboard.domain.models.Dish

data class DashboardState(
    val dishes: List<Dish> = emptyList(),
    val selectedDishId: Int? = null,
    val nombre: String = "",
    val descripcion: String = "",
    val precio: String = "",
    val categoria: String = "",
    val disponible: Boolean = true,
    val nombreError: String? = null,
    val precioError: String? = null,
    val shouldShowNombreError: Boolean = false,
    val shouldShowPrecioError: Boolean = false,
    val isFormValid: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)
