package com.montse.apptransaccional.features.waiter.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.montse.apptransaccional.features.waiter.domain.models.TableStatus
import com.montse.apptransaccional.features.waiter.presentation.components.WaiterTableCard
import com.montse.apptransaccional.features.waiter.presentation.viewmodels.WaiterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WaiterHomeScreen(
    onLogout: () -> Unit,
    onOpenSession: (sessionId: Int, tableNumber: Int) -> Unit,
    viewModel: WaiterViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val foodlyPink = Color(0xFFE91E63)

    LaunchedEffect(Unit) {
        viewModel.loadTables()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Mesas", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        state.user?.let {
                            Text(
                                text = it.name,
                                fontSize = 12.sp,
                                color = Color.White.copy(alpha = 0.8f)
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = foodlyPink,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),
                actions = {
                    IconButton(onClick = { viewModel.loadTables() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Recargar")
                    }
                    IconButton(onClick = {
                        viewModel.logout()
                        onLogout()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Cerrar sesion")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Status summary bar
            if (state.tables.isNotEmpty()) {
                val libres = state.tables.count { it.status == TableStatus.LIBRE }
                val ocupadas = state.tables.count { it.status == TableStatus.OCUPADA }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatusChip(
                        label = "$libres Libres",
                        color = Color(0xFF4CAF50),
                        modifier = Modifier.weight(1f)
                    )
                    StatusChip(
                        label = "$ocupadas Ocupadas",
                        color = foodlyPink,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            if (state.error != null) {
                Text(
                    text = state.error!!,
                    color = Color.Red,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
            }

            when {
                state.isLoading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = foodlyPink)
                    }
                }
                state.tables.isEmpty() && state.error == null -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No hay mesas disponibles", color = Color.Gray)
                    }
                }
                else -> {
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
                            WaiterTableCard(
                                table = table,
                                onTap = {
                                    if (table.status == TableStatus.LIBRE) {
                                        viewModel.openSession(table.id, onOpenSession)
                                    } else if (table.sessionId != null) {
                                        onOpenSession(table.sessionId, table.number)
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }

        // Opening session overlay
        if (state.isOpeningSession) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    color = Color.Black.copy(alpha = 0.3f),
                    modifier = Modifier.fillMaxSize()
                ) {}
                Card(shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)) {
                    Column(
                        modifier = Modifier.padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(color = foodlyPink)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Abriendo mesa...", fontWeight = FontWeight.Medium)
                    }
                }
            }
        }
    }
}

@Composable
private fun StatusChip(
    label: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp),
        color = color.copy(alpha = 0.12f)
    ) {
        Text(
            text = label,
            color = color,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            textAlign = TextAlign.Center
        )
    }
}
