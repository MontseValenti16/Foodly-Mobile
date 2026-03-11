package com.montse.apptransaccional.features.users.presentation.viewmodels

import com.montse.apptransaccional.features.users.domain.models.Role
import com.montse.apptransaccional.features.users.domain.models.User

data class UsersManagementState(
    val users: List<User> = emptyList(),
    val roles: List<Role> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingRoles: Boolean = false,
    val error: String? = null,
    val name: String = "",
    val username: String = "",
    val password: String = "",
    val roleId: Int = 2,
    val isSubmitting: Boolean = false,
    val nameError: String? = null,
    val usernameError: String? = null,
    val passwordError: String? = null,
    val shouldShowNameError: Boolean = false,
    val shouldShowUsernameError: Boolean = false,
    val shouldShowPasswordError: Boolean = false,
    val isFormValid: Boolean = false
)
