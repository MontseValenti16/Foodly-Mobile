package com.montse.apptransaccional.features.waiter.presentation.state

import com.montse.apptransaccional.features.users.domain.models.User
import com.montse.apptransaccional.features.waiter.domain.models.WaiterTable

data class WaiterState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val tables: List<WaiterTable> = emptyList(),
    val error: String? = null,
    val isOpeningSession: Boolean = false
)
