package com.montse.apptransaccional.features.waiter.presentation.state

import com.montse.apptransaccional.features.users.domain.models.User

data class WaiterState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String? = null
)
