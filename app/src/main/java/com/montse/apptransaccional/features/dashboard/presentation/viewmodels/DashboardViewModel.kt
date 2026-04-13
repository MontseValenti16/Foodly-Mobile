package com.montse.apptransaccional.features.dashboard.presentation.viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.montse.apptransaccional.features.dashboard.domain.models.Dish
import com.montse.apptransaccional.features.dashboard.domain.usecases.*
import com.montse.apptransaccional.features.dashboard.presentation.state.DashboardState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getDishesUseCase: GetDishesUseCase,
    private val getDishByIdUseCase: GetDishByIdUseCase,
    private val createDishUseCase: CreateDishUseCase,
    private val updateDishUseCase: UpdateDishUseCase,
    private val deleteDishUseCase: DeleteDishUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getAreasUseCase: GetAreasUseCase,
    private val createTempImageUriUseCase: CreateTempImageUriUseCase,
    private val vibrateUseCase: VibrateUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(DashboardState())
    val state: StateFlow<DashboardState> = _state.asStateFlow()
    private var nombreTouched = false
    private var precioTouched = false
    private var attemptedSubmit = false
    private var initializedMode: String? = null
    private var initializedDishId: Int? = null

    fun initCreateForm() {
        loadCategories()
        loadAreas()
        if (initializedMode == "create") return
        onCancelEdit()
        initializedMode = "create"
        initializedDishId = null
    }

    fun initEditForm(id: Int) {
        loadCategories()
        loadAreas()
        if (initializedMode == "edit" && initializedDishId == id) return
        onSelectDish(id)
        initializedMode = "edit"
        initializedDishId = id
    }

    private fun loadCategories() {
        viewModelScope.launch {
            try {
                val categories = getCategoriesUseCase()
                _state.value = _state.value.copy(categories = categories)
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = "Error loading categories: ${e.localizedMessage}")
            }
        }
    }

    private fun loadAreas() {
        viewModelScope.launch {
            try {
                val areas = getAreasUseCase()
                _state.value = _state.value.copy(areas = areas)
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = "Error loading areas: ${e.localizedMessage}")
            }
        }
    }

    fun onCategorySelected(categoryId: Int, categoryName: String) {
        _state.value = _state.value.copy(
            selectedCategoryId = categoryId,
            selectedCategoryName = categoryName
        )
    }

    fun onAreaSelected(areaId: Int, areaName: String) {
        _state.value = _state.value.copy(
            selectedAreaId = areaId,
            selectedAreaName = areaName
        )
    }

    fun loadDishes() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                val dishes = getDishesUseCase()
                _state.value = _state.value.copy(isLoading = false, dishes = dishes)
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, error = e.localizedMessage)
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

    fun onDisponibleChange(value: Boolean) {
        _state.value = _state.value.copy(disponible = value)
    }

    fun onImageSelected(uri: Uri?) {
        _state.value = _state.value.copy(imageUri = uri)
    }

    fun vibrateTriple() {
        vibrateUseCase.vibrateTriple()
    }

    fun getTempImageUri(): Uri? = createTempImageUriUseCase()

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
                    disponible = dish.disponible,
                    imageUri = null,
                    selectedCategoryId = dish.categoryId,
                    selectedCategoryName = dish.categoria ?: "Select category",
                    selectedAreaId = dish.areaId,
                    selectedAreaName = "Area selected"
                ))
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, error = e.localizedMessage)
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
            selectedCategoryId = null,
            selectedCategoryName = "Select category",
            selectedAreaId = null,
            selectedAreaName = "Select area",
            disponible = true,
            imageUri = null,
            error = null
        ))
        initializedMode = null
        initializedDishId = null
    }

    fun saveDish(onSuccess: () -> Unit) {
        viewModelScope.launch {
            markSubmitAttempted()
            val priceValue = _state.value.precio.trim().toDoubleOrNull()
            
            if (!_state.value.isFormValid || priceValue == null || _state.value.selectedCategoryId == null || _state.value.selectedAreaId == null) {
                if (_state.value.selectedCategoryId == null) {
                    _state.value = _state.value.copy(error = "Please select a category")
                } else if (_state.value.selectedAreaId == null) {
                    _state.value = _state.value.copy(error = "Please select an area")
                }
                return@launch
            }

            _state.value = _state.value.copy(isLoading = true, error = null)
            
            val dish = Dish(
                id = _state.value.selectedDishId ?: 0,
                nombre = _state.value.nombre.trim(),
                descripcion = _state.value.descripcion.ifBlank { null },
                precio = priceValue,
                categoria = _state.value.selectedCategoryName,
                disponible = _state.value.disponible,
                imageUrl = null,
                areaId = _state.value.selectedAreaId,
                categoryId = _state.value.selectedCategoryId
            )

            try {
                if (_state.value.selectedDishId == null) {
                    createDishUseCase(dish, _state.value.imageUri)
                } else {
                    updateDishUseCase(dish, _state.value.imageUri)
                }
                onCancelEdit()
                loadDishes()
                onSuccess()
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, error = e.localizedMessage)
            }
        }
    }

    fun deleteDish(id: Int) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                deleteDishUseCase(id)
                loadDishes()
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, error = e.localizedMessage)
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
