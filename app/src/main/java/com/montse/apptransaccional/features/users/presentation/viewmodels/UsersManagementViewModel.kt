package com.montse.apptransaccional.features.users.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.montse.apptransaccional.features.users.domain.usecases.CreateUserUseCase
import com.montse.apptransaccional.features.users.domain.usecases.DeleteUserUseCase
import com.montse.apptransaccional.features.users.domain.usecases.GetRolesUseCase
import com.montse.apptransaccional.features.users.domain.usecases.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersManagementViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val createUserUseCase: CreateUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val getRolesUseCase: GetRolesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UsersManagementState())
    val state: StateFlow<UsersManagementState> = _state.asStateFlow()

    private var nameTouched = false
    private var usernameTouched = false
    private var passwordTouched = false
    private var attemptedSubmit = false

    fun loadUsers() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                _state.value = _state.value.copy(
                    isLoading = false,
                    users = getUsersUseCase()
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Error al cargar empleados: ${e.message}"
                )
            }
        }
    }

    fun loadRoles() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoadingRoles = true)
            try {
                val roles = getRolesUseCase()
                _state.value = _state.value.copy(
                    isLoadingRoles = false,
                    roles = roles
                )
                // Set default roleId to first role id if available
                if (roles.isNotEmpty()) {
                    _state.value = _state.value.copy(roleId = roles.first().id)
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoadingRoles = false,
                    error = "Error al cargar roles: ${e.message}"
                )
            }
        }
    }

    fun onNameChange(value: String) {
        if (!nameTouched) nameTouched = true
        _state.value = applyValidation(_state.value.copy(name = value))
    }

    fun onUsernameChange(value: String) {
        if (!usernameTouched) usernameTouched = true
        _state.value = applyValidation(_state.value.copy(username = value))
    }

    fun onPasswordChange(value: String) {
        if (!passwordTouched) passwordTouched = true
        _state.value = applyValidation(_state.value.copy(password = value))
    }

    fun onRoleSelected(roleId: Int) {
        _state.value = _state.value.copy(roleId = roleId)
    }

    fun createUser(onSuccess: () -> Unit) {
        viewModelScope.launch {
            attemptedSubmit = true
            _state.value = applyValidation(_state.value)
            if (!_state.value.isFormValid) return@launch

            _state.value = _state.value.copy(isSubmitting = true, error = null)
            try {
                val created = createUserUseCase(
                    name = _state.value.name.trim(),
                    username = _state.value.username.trim(),
                    password = _state.value.password,
                    roleId = _state.value.roleId
                )
                _state.value = _state.value.copy(
                    isSubmitting = false,
                    users = listOf(created) + _state.value.users
                )
                resetForm()
                onSuccess()
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isSubmitting = false,
                    error = "Error al crear usuario: ${e.message}"
                )
            }
        }
    }

    fun deleteUser(id: Int) {
        viewModelScope.launch {
            try {
                deleteUserUseCase(id)
                _state.value = _state.value.copy(
                    users = _state.value.users.map { user ->
                        if (user.id == id) user.copy(isActive = false) else user
                    }
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = "Error al eliminar usuario: ${e.message}")
            }
        }
    }

    fun resetForm() {
        nameTouched = false
        usernameTouched = false
        passwordTouched = false
        attemptedSubmit = false
        _state.value = _state.value.copy(
            name = "",
            username = "",
            password = "",
            roleId = 2,
            nameError = null,
            usernameError = null,
            passwordError = null,
            shouldShowNameError = false,
            shouldShowUsernameError = false,
            shouldShowPasswordError = false,
            isFormValid = false,
            error = null
        )
    }

    private fun applyValidation(base: UsersManagementState): UsersManagementState {
        val nameError = when {
            base.name.trim().isEmpty() -> "El nombre es obligatorio"
            base.name.trim().length < 3 -> "Debe tener al menos 3 caracteres"
            else -> null
        }
        val usernameError = when {
            base.username.trim().isEmpty() -> "El username es obligatorio"
            base.username.trim().length < 3 -> "Debe tener al menos 3 caracteres"
            else -> null
        }
        val passwordError = when {
            base.password.isEmpty() -> "La contraseña es obligatoria"
            base.password.length < 6 -> "La contraseña debe tener al menos 6 caracteres"
            else -> null
        }
        return base.copy(
            nameError = nameError,
            usernameError = usernameError,
            passwordError = passwordError,
            shouldShowNameError = (nameTouched || attemptedSubmit) && nameError != null,
            shouldShowUsernameError = (usernameTouched || attemptedSubmit) && usernameError != null,
            shouldShowPasswordError = (passwordTouched || attemptedSubmit) && passwordError != null,
            isFormValid = nameError == null && usernameError == null && passwordError == null
        )
    }
}
