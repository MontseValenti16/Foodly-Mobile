package com.montse.apptransaccional.features.dashboard.presentation.state

import android.net.Uri
import com.montse.apptransaccional.features.dashboard.domain.models.Area
import com.montse.apptransaccional.features.dashboard.domain.models.Category
import com.montse.apptransaccional.features.dashboard.domain.models.Dish

data class DashboardState(
    val dishes: List<Dish> = emptyList(),
    val categories: List<Category> = emptyList(),
    val areas: List<Area> = emptyList(),
    val selectedDishId: Int? = null,
    val nombre: String = "",
    val descripcion: String = "",
    val precio: String = "",
    val selectedCategoryId: Int? = null,
    val selectedCategoryName: String = "Select category",
    val selectedAreaId: Int? = null,
    val selectedAreaName: String = "Select area",
    val disponible: Boolean = true,
    val imageUri: Uri? = null,
    val nombreError: String? = null,
    val precioError: String? = null,
    val shouldShowNombreError: Boolean = false,
    val shouldShowPrecioError: Boolean = false,
    val isFormValid: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)
