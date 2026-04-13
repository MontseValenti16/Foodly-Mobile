package com.montse.apptransaccional.features.users.presentation.viewmodels

import com.montse.apptransaccional.features.users.domain.models.User

data class ProfileState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String? = null
)
