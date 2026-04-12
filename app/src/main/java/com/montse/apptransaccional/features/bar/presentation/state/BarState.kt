package com.montse.apptransaccional.features.bar.presentation.state

import com.montse.apptransaccional.features.users.domain.models.User

data class BarState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String? = null
)
