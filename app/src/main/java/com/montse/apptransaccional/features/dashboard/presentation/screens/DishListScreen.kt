package com.montse.apptransaccional.features.dashboard.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.montse.apptransaccional.features.dashboard.presentation.components.BottomNavigationBar
import com.montse.apptransaccional.features.dashboard.presentation.components.DashboardHeader
import com.montse.apptransaccional.features.dashboard.presentation.components.DishCard
import com.montse.apptransaccional.features.dashboard.presentation.viewmodels.DashboardViewModel

@Composable
fun DishListScreen(
    onCreate: () -> Unit,
    onEdit: (Int) -> Unit,
    onNavigate: (String) -> Unit,
    currentRoute: String,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val foodlyPink = Color(0xFFE91E63)
    
    var selectedCategory by remember { mutableStateOf("Food") }
    var selectedFilter by remember { mutableStateOf("Todo") }

    LaunchedEffect(Unit) {
        viewModel.loadDishes()
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                currentRoute = currentRoute,
                onNavigate = onNavigate
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.White),
            verticalArrangement = Arrangement.Top
        ) {
            DashboardHeader(onCreate = onCreate)

            // Main Categories
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val categories = listOf("Food", "Drinks", "Desserts")
                items(categories) { category ->
                    FilterChip(
                        selected = selectedCategory == category,
                        onClick = { selectedCategory = category },
                        label = { Text(text = category, fontSize = 16.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = foodlyPink,
                            selectedLabelColor = Color.White,
                            containerColor = Color(0xFFF2F2F2),
                            labelColor = Color.Gray
                        ),
                        border = null,
                        shape = RoundedCornerShape(16.dp)
                    )
                }
            }

            // Sub Filters
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val filters = listOf("Todo", "Desayuno", "Comida", "Cena")
                items(filters) { filter ->
                    FilterChip(
                        selected = selectedFilter == filter,
                        onClick = { selectedFilter = filter },
                        label = { Text(text = filter, fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = foodlyPink,
                            selectedLabelColor = Color.White,
                            containerColor = Color(0xFFF2F2F2),
                            labelColor = Color.Gray
                        ),
                        border = null,
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = foodlyPink)
                }
            } else {
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 12.dp),
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
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
}
