package com.montse.apptransaccional.features.kitchen.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.montse.apptransaccional.core.messaging.OrderEvent
import com.montse.apptransaccional.core.messaging.OrderEventBus
import com.montse.apptransaccional.core.network.RestaurantApi
import com.montse.apptransaccional.core.session.SessionManager
import com.montse.apptransaccional.features.kitchen.presentation.state.KitchenState
import com.montse.apptransaccional.features.users.domain.usecases.GetUserByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KitchenViewModel @Inject constructor(
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val api: RestaurantApi,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _state = MutableStateFlow(KitchenState())
    val state: StateFlow<KitchenState> = _state.asStateFlow()

    init {
        loadProfile()
        loadItems()
        observeEvents()
    }

    private fun observeEvents() {
        viewModelScope.launch {
            OrderEventBus.events.collect { event ->
                if (event is OrderEvent.NewOrder) {
                    refreshItems()
                }
            }
        }
    }

    private fun refreshItems() {
        val areaId = sessionManager.getAreaId()
        if (areaId == -1) return

        viewModelScope.launch {
            try {
                val items = api.getItemsByArea(areaId)
                _state.value = _state.value.copy(items = items, error = null)
            } catch (_: Exception) { }
        }
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

    fun loadItems() {
        val areaId = sessionManager.getAreaId()
        if (areaId == -1) {
            _state.value = _state.value.copy(error = "No se encontro el area asignada")
            return
        }

        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                val items = api.getItemsByArea(areaId)
                _state.value = _state.value.copy(isLoading = false, items = items)
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Error al cargar pedidos: ${e.localizedMessage ?: "Error desconocido"}"
                )
            }
        }
    }

    fun markPreparing(itemId: Int) {
        updateStatus(itemId, "preparing")
    }

    fun markReady(itemId: Int) {
        updateStatus(itemId, "ready")
    }

    private fun updateStatus(itemId: Int, status: String) {
        viewModelScope.launch {
            try {
                api.updateItemStatus(itemId, mapOf("status" to status))
                loadItems()
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = "Error al actualizar: ${e.localizedMessage ?: "Error"}"
                )
            }
        }
    }

    fun logout() {
        sessionManager.clear()
    }
}
