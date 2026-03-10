package com.montse.apptransaccional.features.dashboard.presentation.screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.montse.apptransaccional.features.dashboard.presentation.components.DishForm
import com.montse.apptransaccional.features.dashboard.presentation.viewmodels.DashboardViewModel

@Composable
fun EditDishScreen(
    dishId: Int,
    onBack: () -> Unit,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    DishForm(
        title = "Editar platillo",
        viewModel = viewModel,
        onLoad = { viewModel.initEditForm(dishId) },
        onSave = { viewModel.saveDish { onBack() } },
        onCancel = {
            viewModel.onCancelEdit()
            onBack()
        }
    )
}
