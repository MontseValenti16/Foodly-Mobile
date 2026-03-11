package com.montse.apptransaccional.features.dashboard.presentation.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
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
        title = "Edit dish",
        icon = Icons.Default.Edit,
        viewModel = viewModel,
        onLoad = { viewModel.initEditForm(dishId) },
        onSave = { viewModel.saveDish { onBack() } },
        onCancel = {
            viewModel.onCancelEdit()
            onBack()
        }
    )
}
