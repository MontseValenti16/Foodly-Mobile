package com.montse.apptransaccional.features.dashboard.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.montse.apptransaccional.features.dashboard.domain.usecases.CreateDishUseCase
import com.montse.apptransaccional.features.dashboard.domain.usecases.DeleteDishUseCase
import com.montse.apptransaccional.features.dashboard.domain.usecases.GetDishByIdUseCase
import com.montse.apptransaccional.features.dashboard.domain.usecases.GetDishesUseCase
import com.montse.apptransaccional.features.dashboard.domain.usecases.UpdateDishUseCase

class DashboardViewModelFactory(
	private val getDishesUseCase: GetDishesUseCase,
	private val getDishByIdUseCase: GetDishByIdUseCase,
	private val createDishUseCase: CreateDishUseCase,
	private val updateDishUseCase: UpdateDishUseCase,
	private val deleteDishUseCase: DeleteDishUseCase
) : ViewModelProvider.Factory {
	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		return DashboardViewModel(
			getDishesUseCase,
			getDishByIdUseCase,
			createDishUseCase,
			updateDishUseCase,
			deleteDishUseCase
		) as T
	}
}