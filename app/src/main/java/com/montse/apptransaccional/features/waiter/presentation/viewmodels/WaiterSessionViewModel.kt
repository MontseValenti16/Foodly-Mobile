package com.montse.apptransaccional.features.waiter.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.montse.apptransaccional.features.dashboard.domain.models.Dish
import com.montse.apptransaccional.features.waiter.domain.models.CartItem
import com.montse.apptransaccional.features.waiter.domain.usecases.CloseSessionUseCase
import com.montse.apptransaccional.features.waiter.domain.usecases.GetProductsUseCase
import com.montse.apptransaccional.features.waiter.domain.usecases.GetSessionOrdersUseCase
import com.montse.apptransaccional.features.waiter.domain.usecases.GetWaiterCategoriesUseCase
import com.montse.apptransaccional.features.waiter.domain.usecases.SendOrderUseCase
import com.montse.apptransaccional.features.waiter.domain.usecases.UpdateItemStatusUseCase
import com.montse.apptransaccional.features.waiter.presentation.state.WaiterSessionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WaiterSessionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getProductsUseCase: GetProductsUseCase,
    private val getCategoriesUseCase: GetWaiterCategoriesUseCase,
    private val getSessionOrdersUseCase: GetSessionOrdersUseCase,
    private val sendOrderUseCase: SendOrderUseCase,
    private val updateItemStatusUseCase: UpdateItemStatusUseCase,
    private val closeSessionUseCase: CloseSessionUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(WaiterSessionState())
    val state: StateFlow<WaiterSessionState> = _state.asStateFlow()

    init {
        val sessionId = savedStateHandle.get<String>("sessionId")?.toIntOrNull() ?: 0
        val tableNumber = savedStateHandle.get<String>("tableNumber")?.toIntOrNull() ?: 0
        _state.value = _state.value.copy(sessionId = sessionId, tableNumber = tableNumber)
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                val products = getProductsUseCase()
                val categories = getCategoriesUseCase()
                val orders = getSessionOrdersUseCase(_state.value.sessionId)

                _state.value = _state.value.copy(
                    isLoading = false,
                    products = products,
                    categories = categories,
                    orders = orders
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Error al cargar datos: ${e.localizedMessage ?: "Error desconocido"}"
                )
            }
        }
    }

    fun selectCategory(category: String) {
        _state.value = _state.value.copy(selectedCategory = category)
    }

    fun addToCart(dish: Dish) {
        val currentCart = _state.value.cart.toMutableList()
        val existingIndex = currentCart.indexOfFirst { it.productId == dish.id }

        if (existingIndex >= 0) {
            val existing = currentCart[existingIndex]
            currentCart[existingIndex] = existing.copy(quantity = existing.quantity + 1)
        } else {
            currentCart.add(
                CartItem(
                    productId = dish.id,
                    name = dish.nombre,
                    price = dish.precio,
                    quantity = 1
                )
            )
        }

        _state.value = _state.value.copy(cart = currentCart, orderSentSuccess = false)
    }

    fun removeFromCart(productId: Int) {
        val currentCart = _state.value.cart.toMutableList()
        val existingIndex = currentCart.indexOfFirst { it.productId == productId }

        if (existingIndex >= 0) {
            val existing = currentCart[existingIndex]
            if (existing.quantity > 1) {
                currentCart[existingIndex] = existing.copy(quantity = existing.quantity - 1)
            } else {
                currentCart.removeAt(existingIndex)
            }
        }

        _state.value = _state.value.copy(cart = currentCart)
    }

    fun clearCart() {
        _state.value = _state.value.copy(cart = emptyList())
    }

    fun sendOrder() {
        if (_state.value.cart.isEmpty()) return

        viewModelScope.launch {
            _state.value = _state.value.copy(isSendingOrder = true, error = null, orderSentSuccess = false)
            try {
                sendOrderUseCase(_state.value.sessionId, _state.value.cart)
                val orders = getSessionOrdersUseCase(_state.value.sessionId)

                _state.value = _state.value.copy(
                    isSendingOrder = false,
                    cart = emptyList(),
                    orders = orders,
                    orderSentSuccess = true
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isSendingOrder = false,
                    error = "Error al enviar pedido: ${e.localizedMessage ?: "Error desconocido"}"
                )
            }
        }
    }

    fun dismissSuccess() {
        _state.value = _state.value.copy(orderSentSuccess = false)
    }

    // ── Orders panel ────────────────────────────────────────────

    fun toggleOrdersPanel() {
        _state.value = _state.value.copy(showOrdersPanel = !_state.value.showOrdersPanel)
    }

    fun markItemDelivered(itemId: Int) {
        viewModelScope.launch {
            try {
                updateItemStatusUseCase(itemId, "delivered")
                val orders = getSessionOrdersUseCase(_state.value.sessionId)
                _state.value = _state.value.copy(orders = orders)
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = "Error al marcar entregado: ${e.localizedMessage ?: "Error"}"
                )
            }
        }
    }

    // ── Close session / Ticket ──────────────────────────────────

    fun showCloseDialog() {
        _state.value = _state.value.copy(showCloseDialog = true, error = null)
    }

    fun dismissCloseDialog() {
        _state.value = _state.value.copy(showCloseDialog = false)
    }

    fun onPaymentMethodChange(method: String) {
        _state.value = _state.value.copy(paymentMethod = method)
    }

    fun onTipChange(value: String) {
        _state.value = _state.value.copy(tip = value)
    }

    fun onDiscountChange(value: String) {
        _state.value = _state.value.copy(discount = value)
    }

    fun closeSession() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isClosingSession = true, error = null, showCloseDialog = false)
            try {
                val ticket = closeSessionUseCase(
                    sessionId = _state.value.sessionId,
                    paymentMethod = _state.value.paymentMethod,
                    tip = _state.value.tip.toDoubleOrNull() ?: 0.0,
                    discount = _state.value.discount.toDoubleOrNull() ?: 0.0
                )
                _state.value = _state.value.copy(
                    isClosingSession = false,
                    ticket = ticket,
                    showTicket = true,
                    sessionClosed = true
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isClosingSession = false,
                    error = "Error al cerrar cuenta: ${e.localizedMessage ?: "Error desconocido"}"
                )
            }
        }
    }

    fun dismissTicket() {
        _state.value = _state.value.copy(showTicket = false)
    }
}
