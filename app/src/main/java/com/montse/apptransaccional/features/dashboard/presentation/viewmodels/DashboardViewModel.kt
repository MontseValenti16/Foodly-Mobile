package com.montse.apptransaccional.features.dashboard.presentation.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.montse.apptransaccional.features.dashboard.domain.models.Dish
import com.montse.apptransaccional.features.dashboard.domain.usecases.CreateDishUseCase
import com.montse.apptransaccional.features.dashboard.domain.usecases.DeleteDishUseCase
import com.montse.apptransaccional.features.dashboard.domain.usecases.GetDishByIdUseCase
import com.montse.apptransaccional.features.dashboard.domain.usecases.GetDishesUseCase
import com.montse.apptransaccional.features.dashboard.domain.usecases.UpdateDishUseCase
import com.montse.apptransaccional.features.dashboard.presentation.state.DashboardState
import kotlinx.coroutines.launch

class DashboardViewModel(
	private val getDishesUseCase: GetDishesUseCase,
	private val getDishByIdUseCase: GetDishByIdUseCase,
	private val createDishUseCase: CreateDishUseCase,
	private val updateDishUseCase: UpdateDishUseCase,
	private val deleteDishUseCase: DeleteDishUseCase
) : ViewModel() {

	var state by mutableStateOf(DashboardState())
		private set

	fun loadDishes() {
		viewModelScope.launch {
			state = state.copy(isLoading = true, error = null)
			try {
				val dishes = getDishesUseCase()
				state = state.copy(isLoading = false, dishes = dishes)
			} catch (e: Exception) {
				state = state.copy(isLoading = false, error = e.message)
			}
		}
	}

	fun onNombreChange(value: String) {
		state = state.copy(nombre = value)
	}

	fun onDescripcionChange(value: String) {
		state = state.copy(descripcion = value)
	}

	fun onPrecioChange(value: String) {
		state = state.copy(precio = value)
	}

	fun onCategoriaChange(value: String) {
		state = state.copy(categoria = value)
	}

	fun onDisponibleChange(value: Boolean) {
		state = state.copy(disponible = value)
	}

	fun onSelectDish(id: Int) {
		viewModelScope.launch {
			state = state.copy(isLoading = true, error = null)
			try {
				val dish = getDishByIdUseCase(id)
				state = state.copy(
					isLoading = false,
					selectedDishId = dish.id,
					nombre = dish.nombre,
					descripcion = dish.descripcion ?: "",
					precio = dish.precio.toString(),
					categoria = dish.categoria ?: "",
					disponible = dish.disponible
				)
			} catch (e: Exception) {
				state = state.copy(isLoading = false, error = e.message)
			}
		}
	}

	fun onCancelEdit() {
		state = state.copy(
			selectedDishId = null,
			nombre = "",
			descripcion = "",
			precio = "",
			categoria = "",
			disponible = true,
			error = null
		)
	}

	fun saveDish(onSuccess: () -> Unit) {
		viewModelScope.launch {
			val priceValue = state.precio.toDoubleOrNull()
			if (state.nombre.isBlank() || priceValue == null) {
				state = state.copy(error = "Nombre y precio son obligatorios")
				return@launch
			}

			state = state.copy(isLoading = true, error = null)
			val dish = Dish(
				id = state.selectedDishId ?: 0,
				nombre = state.nombre.trim(),
				descripcion = state.descripcion.ifBlank { null },
				precio = priceValue,
				categoria = state.categoria.ifBlank { null },
				disponible = state.disponible
			)

			try {
				if (state.selectedDishId == null) {
					createDishUseCase(dish)
				} else {
					updateDishUseCase(dish)
				}
				onCancelEdit()
				val dishes = getDishesUseCase()
				state = state.copy(isLoading = false, dishes = dishes)
				onSuccess()
			} catch (e: Exception) {
				state = state.copy(isLoading = false, error = e.message)
			}
		}
	}

	fun deleteDish(id: Int) {
		viewModelScope.launch {
			state = state.copy(isLoading = true, error = null)
			try {
				deleteDishUseCase(id)
				val dishes = getDishesUseCase()
				state = state.copy(isLoading = false, dishes = dishes)
			} catch (e: Exception) {
				state = state.copy(isLoading = false, error = e.message)
			}
		}
	}
}

