package com.montse.apptransaccional.features.kitchen.presentation.state

import com.montse.apptransaccional.features.users.domain.models.User

data class KitchenState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String? = null
)
