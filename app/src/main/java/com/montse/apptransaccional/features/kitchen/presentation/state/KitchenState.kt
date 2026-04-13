package com.montse.apptransaccional.features.kitchen.presentation.state

import com.montse.apptransaccional.core.data.remote.AreaItemDto
import com.montse.apptransaccional.features.users.domain.models.User

data class KitchenState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val items: List<AreaItemDto> = emptyList(),
    val error: String? = null
) {
    val pendingItems: List<AreaItemDto>
        get() = items.filter { it.status == "pending" }

    val preparingItems: List<AreaItemDto>
        get() = items.filter { it.status == "preparing" }
}
