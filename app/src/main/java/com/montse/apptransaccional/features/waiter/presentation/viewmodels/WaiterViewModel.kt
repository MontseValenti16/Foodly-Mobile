package com.montse.apptransaccional.features.waiter.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.montse.apptransaccional.core.session.SessionManager
import com.montse.apptransaccional.features.users.domain.usecases.GetUserByIdUseCase
import com.montse.apptransaccional.features.waiter.domain.usecases.GetWaiterTablesUseCase
import com.montse.apptransaccional.features.waiter.domain.usecases.OpenSessionUseCase
import com.montse.apptransaccional.features.waiter.presentation.state.WaiterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WaiterViewModel @Inject constructor(
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val getWaiterTablesUseCase: GetWaiterTablesUseCase,
    private val openSessionUseCase: OpenSessionUseCase,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _state = MutableStateFlow(WaiterState())
    val state: StateFlow<WaiterState> = _state.asStateFlow()

    init {
        loadProfile()
        loadTables()
    }

    fun loadProfile() {
        val userId = sessionManager.getUserId()
        if (userId == -1) return

        viewModelScope.launch {
            try {
                val user = getUserByIdUseCase(userId)
                _state.value = _state.value.copy(user = user)
            } catch (_: Exception) { }
        }
    }

    fun loadTables() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                val tables = getWaiterTablesUseCase()
                _state.value = _state.value.copy(isLoading = false, tables = tables)
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Error al cargar mesas: ${e.localizedMessage ?: "Error desconocido"}"
                )
            }
        }
    }

    fun openSession(tableId: Int) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isOpeningSession = true, error = null)
            try {
                openSessionUseCase(tableId)
                loadTables()
                _state.value = _state.value.copy(isOpeningSession = false)
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isOpeningSession = false,
                    error = "Error al abrir mesa: ${e.localizedMessage ?: "Error desconocido"}"
                )
            }
        }
    }

    fun logout() {
        sessionManager.clear()
    }
}
