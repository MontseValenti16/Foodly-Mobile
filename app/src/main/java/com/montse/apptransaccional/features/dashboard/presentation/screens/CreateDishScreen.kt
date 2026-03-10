package com.montse.apptransaccional.features.dashboard.presentation.screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.montse.apptransaccional.features.dashboard.presentation.components.DishForm
import com.montse.apptransaccional.features.dashboard.presentation.viewmodels.DashboardViewModel

@Composable
fun CreateDishScreen(
    onBack: () -> Unit,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    DishForm(
        title = "Crear platillo",
        viewModel = viewModel,
        onLoad = viewModel::initCreateForm,
        onSave = { viewModel.saveDish { onBack() } },
        onCancel = {
            viewModel.onCancelEdit()
            onBack()
        }
    )
}
