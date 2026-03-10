package com.montse.apptransaccional.features.dashboard.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.montse.apptransaccional.features.dashboard.domain.models.Dish
import com.montse.apptransaccional.features.dashboard.domain.usecases.CreateDishUseCase
import com.montse.apptransaccional.features.dashboard.domain.usecases.DeleteDishUseCase
import com.montse.apptransaccional.features.dashboard.domain.usecases.GetDishByIdUseCase
import com.montse.apptransaccional.features.dashboard.domain.usecases.GetDishesUseCase
import com.montse.apptransaccional.features.dashboard.domain.usecases.UpdateDishUseCase
import com.montse.apptransaccional.features.dashboard.presentation.state.DashboardState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class DashboardViewModel(
	private val getDishesUseCase: GetDishesUseCase,
	private val getDishByIdUseCase: GetDishByIdUseCase,
	private val createDishUseCase: CreateDishUseCase,
	private val updateDishUseCase: UpdateDishUseCase,
	private val deleteDishUseCase: DeleteDishUseCase
) : ViewModel() {

	private val _state = MutableStateFlow(DashboardState())
	val state: StateFlow<DashboardState> = _state.asStateFlow()
	private var nombreTouched = false
	private var precioTouched = false
	private var attemptedSubmit = false
	private var initializedMode: String? = null
	private var initializedDishId: Int? = null

	fun initCreateForm() {
		if (initializedMode == "create") return
		onCancelEdit()
		initializedMode = "create"
		initializedDishId = null
	}

	fun initEditForm(id: Int) {
		if (initializedMode == "edit" && initializedDishId == id) return
		onSelectDish(id)
		initializedMode = "edit"
		initializedDishId = id
	}

	fun loadDishes() {
		viewModelScope.launch {
			_state.value = _state.value.copy(isLoading = true, error = null)
			try {
				val dishes = getDishesUseCase()
				_state.value = _state.value.copy(isLoading = false, dishes = dishes)
			} catch (e: Exception) {
				_state.value = _state.value.copy(isLoading = false, error = e.message)
			}
		}
	}

	fun onNombreChange(value: String) {
		if (!nombreTouched) nombreTouched = true
		_state.value = applyValidation(_state.value.copy(nombre = value))
	}

	fun onDescripcionChange(value: String) {
		_state.value = _state.value.copy(descripcion = value)
	}

	fun onPrecioChange(value: String) {
		if (!precioTouched) precioTouched = true
		_state.value = applyValidation(_state.value.copy(precio = value))
	}

	fun onCategoriaChange(value: String) {
		_state.value = _state.value.copy(categoria = value)
	}

	fun onDisponibleChange(value: Boolean) {
		_state.value = _state.value.copy(disponible = value)
	}

	fun onSelectDish(id: Int) {
		viewModelScope.launch {
			_state.value = _state.value.copy(isLoading = true, error = null)
			try {
				val dish = getDishByIdUseCase(id)
				resetValidationFlags()
				_state.value = applyValidation(_state.value.copy(
					isLoading = false,
					selectedDishId = dish.id,
					nombre = dish.nombre,
					descripcion = dish.descripcion ?: "",
					precio = dish.precio.toString(),
					categoria = dish.categoria ?: "",
					disponible = dish.disponible
				))
			} catch (e: Exception) {
				_state.value = _state.value.copy(isLoading = false, error = e.message)
			}
		}
	}

	fun onCancelEdit() {
		resetValidationFlags()
		_state.value = applyValidation(_state.value.copy(
			selectedDishId = null,
			nombre = "",
			descripcion = "",
			precio = "",
			categoria = "",
			disponible = true,
			error = null
		))
		initializedMode = null
		initializedDishId = null
	}

	fun saveDish(onSuccess: () -> Unit) {
		viewModelScope.launch {
			markSubmitAttempted()
			val priceValue = _state.value.precio.trim().toDoubleOrNull()
			if (!_state.value.isFormValid || priceValue == null) return@launch

			_state.value = _state.value.copy(isLoading = true, error = null)
			val dish = Dish(
				id = _state.value.selectedDishId ?: 0,
				nombre = _state.value.nombre.trim(),
				descripcion = _state.value.descripcion.ifBlank { null },
				precio = priceValue,
				categoria = _state.value.categoria.ifBlank { null },
				disponible = _state.value.disponible
			)

			try {
				if (_state.value.selectedDishId == null) {
					val createdDish = createDishUseCase(dish)
					onCancelEdit()
					val updatedDishes = _state.value.dishes + createdDish
					_state.value = _state.value.copy(isLoading = false, dishes = updatedDishes)
				} else {
					updateDishUseCase(dish)
					onCancelEdit()
					val updatedDishes = _state.value.dishes.map { existing ->
						if (existing.id == dish.id) dish else existing
					}
					_state.value = _state.value.copy(isLoading = false, dishes = updatedDishes)
				}
				onSuccess()
			} catch (e: Exception) {
				_state.value = _state.value.copy(isLoading = false, error = e.message)
			}
		}
	}

	fun deleteDish(id: Int) {
		viewModelScope.launch {
			_state.value = _state.value.copy(isLoading = true, error = null)
			try {
				deleteDishUseCase(id)
				val updatedDishes = _state.value.dishes.filter { dish -> dish.id != id }
				_state.value = _state.value.copy(isLoading = false, dishes = updatedDishes)
			} catch (e: Exception) {
				_state.value = _state.value.copy(isLoading = false, error = e.message)
			}
		}
	}

	private fun applyValidation(baseState: DashboardState): DashboardState {
		val nombreValue = baseState.nombre.trim()
		val precioValue = baseState.precio.trim().toDoubleOrNull()
		val nombreError = if (nombreValue.isEmpty()) "El nombre es obligatorio" else null
		val precioError = when {
			baseState.precio.trim().isEmpty() -> "El precio es obligatorio"
			precioValue == null -> "El precio debe ser un numero valido"
			precioValue <= 0.0 -> "El precio debe ser mayor a 0"
			else -> null
		}
		val shouldShowNombreError = (nombreTouched || attemptedSubmit) && nombreError != null
		val shouldShowPrecioError = (precioTouched || attemptedSubmit) && precioError != null
		val isFormValid = nombreError == null && precioError == null

		return baseState.copy(
			nombreError = nombreError,
			precioError = precioError,
			shouldShowNombreError = shouldShowNombreError,
			shouldShowPrecioError = shouldShowPrecioError,
			isFormValid = isFormValid
		)
	}

	private fun markSubmitAttempted() {
		attemptedSubmit = true
		_state.value = applyValidation(_state.value)
	}

	private fun resetValidationFlags() {
		nombreTouched = false
		precioTouched = false
		attemptedSubmit = false
	}
}

