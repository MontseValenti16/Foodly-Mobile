package com.montse.apptransaccional.features.tables.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.montse.apptransaccional.features.dashboard.presentation.components.BottomNavigationBar
import com.montse.apptransaccional.features.dashboard.presentation.components.DashboardHeader
import com.montse.apptransaccional.features.tables.presentation.components.TableCard
import com.montse.apptransaccional.features.tables.presentation.viewmodels.TablesViewModel

@Composable
fun TablesScreen(
    onNavigate: (String) -> Unit,
    currentRoute: String,
    onCreate: () -> Unit = { onNavigate("tables/create") },
    viewModel: TablesViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val foodlyPink = Color(0xFFE91E63)

    LaunchedEffect(Unit) {
        viewModel.loadTables()
    }

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            BottomNavigationBar(
                currentRoute = currentRoute,
                onNavigate = onNavigate,
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Top
        ) {
            DashboardHeader(onCreate = onCreate)

            Spacer(modifier = Modifier.height(8.dp))

            if (state.error != null) {
                Text(
                    text = state.error!!,
                    color = Color.Red,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = foodlyPink)
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(state.tables) { table ->
                        TableCard(
                            table = table,
                            onDelete = { viewModel.deleteTable(it) },
                            accentColor = foodlyPink
                        )
                    }
                }
            }
        }
    }
}
