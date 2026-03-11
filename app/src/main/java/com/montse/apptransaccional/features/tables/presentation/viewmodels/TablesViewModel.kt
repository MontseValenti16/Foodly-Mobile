package com.montse.apptransaccional.features.tables.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.montse.apptransaccional.features.tables.domain.usecases.CreateTableUseCase
import com.montse.apptransaccional.features.tables.domain.usecases.DeleteTableUseCase
import com.montse.apptransaccional.features.tables.domain.usecases.GetTablesUseCase
import com.montse.apptransaccional.features.tables.presentation.state.TablesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TablesViewModel @Inject constructor(
    private val getTablesUseCase: GetTablesUseCase,
    private val createTableUseCase: CreateTableUseCase,
    private val deleteTableUseCase: DeleteTableUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(TablesState())
    val state: StateFlow<TablesState> = _state.asStateFlow()

    private var numberTouched = false
    private var attemptedSubmit = false

    fun loadTables() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                val tables = getTablesUseCase()
                _state.value = _state.value.copy(isLoading = false, tables = tables)
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, error = e.message)
            }
        }
    }

    fun onNumberChange(value: String) {
        if (!numberTouched) numberTouched = true
        _state.value = applyValidation(_state.value.copy(number = value))
    }

    fun onCapacityIncrement() {
        val current = _state.value.capacity
        if (current < 20) _state.value = _state.value.copy(capacity = current + 1)
    }

    fun onCapacityDecrement() {
        val current = _state.value.capacity
        if (current > 1) _state.value = _state.value.copy(capacity = current - 1)
    }

    fun createTable(onSuccess: () -> Unit) {
        viewModelScope.launch {
            attemptedSubmit = true
            _state.value = applyValidation(_state.value)
            if (!_state.value.isFormValid) return@launch

            _state.value = _state.value.copy(isSubmitting = true, error = null)
            try {
                val table = createTableUseCase(
                    number = _state.value.number.trim().toInt(),
                    capacity = _state.value.capacity
                )
                _state.value = _state.value.copy(
                    isSubmitting = false,
                    tables = _state.value.tables + table
                )
                resetForm()
                onSuccess()
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isSubmitting = false,
                    error = "Error al crear mesa: ${e.message}"
                )
            }
        }
    }

    fun deleteTable(id: Int) {
        viewModelScope.launch {
            _state.value = _state.value.copy(error = null)
            try {
                deleteTableUseCase(id)
                _state.value = _state.value.copy(
                    tables = _state.value.tables.filter { it.id != id }
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = "Error al eliminar mesa: ${e.message}")
            }
        }
    }

    fun resetForm() {
        numberTouched = false
        attemptedSubmit = false
        _state.value = _state.value.copy(
            number = "",
            capacity = 4,
            numberError = null,
            shouldShowNumberError = false,
            isFormValid = false,
            error = null
        )
    }

    private fun applyValidation(base: TablesState): TablesState {
        val num = base.number.trim()
        val numberError = when {
            num.isEmpty() -> "El número de mesa es obligatorio"
            num.toIntOrNull() == null -> "Debe ser un número válido"
            num.toInt() <= 0 -> "El número debe ser mayor a 0"
            else -> null
        }
        val shouldShowNumberError = (numberTouched || attemptedSubmit) && numberError != null
        return base.copy(
            numberError = numberError,
            shouldShowNumberError = shouldShowNumberError,
            isFormValid = numberError == null
        )
    }
}
