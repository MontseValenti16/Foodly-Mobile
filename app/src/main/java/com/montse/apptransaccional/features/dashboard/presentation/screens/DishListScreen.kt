package com.montse.apptransaccional.features.dashboard.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.montse.apptransaccional.features.dashboard.presentation.components.DashboardHeader
import com.montse.apptransaccional.features.dashboard.presentation.components.DishCard
import com.montse.apptransaccional.features.dashboard.presentation.viewmodels.DashboardViewModel

@Composable
fun DishListScreen(
    onCreate: () -> Unit,
    onEdit: (Int) -> Unit,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val foodlyPink = Color(0xFFE91E63)

    LaunchedEffect(Unit) {
        viewModel.loadDishes()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp, 40.dp ),
        verticalArrangement = Arrangement.Top
    ) {
        DashboardHeader(onCreate = onCreate)

        Spacer(modifier = Modifier.height(16.dp))

        if (state.isLoading) {
            CircularProgressIndicator()
        } else {
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.dishes) { dish ->
                    DishCard(
                        dish = dish,
                        onEdit = onEdit,
                        onDelete = viewModel::deleteDish,
                        accentColor = foodlyPink
                    )
                }
            }
        }
    }
}
