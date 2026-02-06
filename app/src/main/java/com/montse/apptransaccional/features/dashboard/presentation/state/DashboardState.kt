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
    val isLoading: Boolean = false,
    val error: String? = null
)
