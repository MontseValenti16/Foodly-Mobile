package com.montse.apptransaccional.core.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    data object Login : Screen

    @Serializable
    data object Dashboard : Screen

    @Serializable
    data object CreateDish : Screen

    @Serializable
    data class EditDish(val id: Int) : Screen
}
