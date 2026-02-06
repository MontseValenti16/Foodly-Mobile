package com.montse.apptransaccional.features.dashboard.presentation.screens

import androidx.compose.runtime.Composable
import com.montse.apptransaccional.features.dashboard.presentation.components.DishForm
import com.montse.apptransaccional.features.dashboard.presentation.viewmodels.DashboardViewModel

@Composable
fun EditDishScreen(
    viewModel: DashboardViewModel,
    dishId: Int,
    onBack: () -> Unit
) {
    DishForm(
        title = "Editar platillo",
        viewModel = viewModel,
        onLoad = { viewModel.onSelectDish(dishId) },
        onSave = { viewModel.saveDish { onBack() } },
        onCancel = {
            viewModel.onCancelEdit()
            onBack()
        }
    )
}
