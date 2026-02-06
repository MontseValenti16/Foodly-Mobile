package com.montse.apptransaccional.features.dashboard.di

import com.montse.apptransaccional.core.di.AppContainer
import com.montse.apptransaccional.features.dashboard.data.repositories.DishRepositoryImpl
import com.montse.apptransaccional.features.dashboard.domain.usecases.CreateDishUseCase
import com.montse.apptransaccional.features.dashboard.domain.usecases.DeleteDishUseCase
import com.montse.apptransaccional.features.dashboard.domain.usecases.GetDishByIdUseCase
import com.montse.apptransaccional.features.dashboard.domain.usecases.GetDishesUseCase
import com.montse.apptransaccional.features.dashboard.domain.usecases.UpdateDishUseCase
import com.montse.apptransaccional.features.dashboard.presentation.viewmodels.DashboardViewModelFactory

class DashboardModule(appContainer: AppContainer) {
	private val repository = DishRepositoryImpl(appContainer.restaurantApi)
	private val getDishesUseCase = GetDishesUseCase(repository)
	private val getDishByIdUseCase = GetDishByIdUseCase(repository)
	private val createDishUseCase = CreateDishUseCase(repository)
	private val updateDishUseCase = UpdateDishUseCase(repository)
	private val deleteDishUseCase = DeleteDishUseCase(repository)

	fun provideDashboardViewModelFactory(): DashboardViewModelFactory {
		return DashboardViewModelFactory(
			getDishesUseCase,
			getDishByIdUseCase,
			createDishUseCase,
			updateDishUseCase,
			deleteDishUseCase
		)
	}
}