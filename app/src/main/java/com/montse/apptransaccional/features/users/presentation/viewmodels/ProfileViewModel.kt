package com.montse.apptransaccional.features.users.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.montse.apptransaccional.core.session.SessionManager
import com.montse.apptransaccional.features.users.domain.usecases.GetUserByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    init {
        loadUserProfile()
    }

    fun loadUserProfile() {
        val userId = sessionManager.getUserId()
        if (userId == -1) {
            _state.value = _state.value.copy(error = "No se encontró el ID de usuario")
            return
        }

        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                val user = getUserByIdUseCase(userId)
                _state.value = _state.value.copy(isLoading = false, user = user)
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Error al cargar el perfil: ${e.localizedMessage ?: "Error desconocido"}"
                )
            }
        }
    }
}
