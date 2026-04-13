package com.montse.apptransaccional.features.dashboard.presentation.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
        title = "Create dish",
        icon = Icons.Default.Add,
        viewModel = viewModel,
        onLoad = viewModel::initCreateForm,
        onSave = { viewModel.saveDish { onBack() } },
        onCancel = {
            viewModel.onCancelEdit()
            onBack()
        }
    )
}
