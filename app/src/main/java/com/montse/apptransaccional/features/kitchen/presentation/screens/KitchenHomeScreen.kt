package com.montse.apptransaccional.features.kitchen.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.montse.apptransaccional.features.kitchen.presentation.components.atoms.SummaryChip
import com.montse.apptransaccional.features.kitchen.presentation.components.molecules.AreaOrderCard
import com.montse.apptransaccional.features.kitchen.presentation.viewmodels.KitchenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KitchenHomeScreen(
    onLogout: () -> Unit,
    viewModel: KitchenViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val foodlyPink = Color(0xFFE91E63)

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Cocina", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        state.user?.let {
                            Text(it.name, fontSize = 12.sp, color = Color.White.copy(alpha = 0.8f))
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = foodlyPink,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),
                actions = {
                    IconButton(onClick = { viewModel.loadItems() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Recargar")
                    }
                    IconButton(onClick = { viewModel.logout(); onLogout() }) {
                        Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Salir")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.White)
        ) {
            if (state.items.isNotEmpty()) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    SummaryChip("${state.pendingItems.size} Nuevos", Color(0xFFFF9800), Modifier.weight(1f))
                    SummaryChip("${state.preparingItems.size} Preparando", foodlyPink, Modifier.weight(1f))
                }
            }

            if (state.error != null) {
                Text(state.error!!, color = Color.Red, fontSize = 13.sp, modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp))
            }

            when {
                state.isLoading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = foodlyPink)
                    }
                }
                state.items.isEmpty() && state.error == null -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.CheckCircle, null, Modifier.size(64.dp), tint = Color(0xFF4CAF50))
                            Spacer(Modifier.height(12.dp))
                            Text("Sin pedidos pendientes", fontWeight = FontWeight.Medium, fontSize = 18.sp, color = Color.Gray)
                        }
                    }
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(state.items) { item ->
                            AreaOrderCard(
                                item = item,
                                accentColor = foodlyPink,
                                onMarkPreparing = { viewModel.markPreparing(item.id) },
                                onMarkReady = { viewModel.markReady(item.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}
