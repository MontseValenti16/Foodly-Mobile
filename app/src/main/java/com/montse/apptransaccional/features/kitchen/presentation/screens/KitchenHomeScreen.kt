package com.montse.apptransaccional.features.kitchen.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.SoupKitchen
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
import com.montse.apptransaccional.features.kitchen.presentation.viewmodels.KitchenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KitchenHomeScreen(
    onLogout: () -> Unit,
    viewModel: KitchenViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val kitchenOrange = Color(0xFFE05C2A)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Cocina", fontWeight = FontWeight.Bold, fontSize = 18.sp)
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
                    containerColor = kitchenOrange,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),
                actions = {
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(color = kitchenOrange)
                }
                state.error != null -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = state.error!!, color = Color.Red)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { viewModel.loadProfile() },
                            colors = ButtonDefaults.buttonColors(containerColor = kitchenOrange)
                        ) {
                            Text("Reintentar")
                        }
                    }
                }
                else -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.SoupKitchen,
                            contentDescription = null,
                            modifier = Modifier.size(80.dp),
                            tint = kitchenOrange
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Panel de Cocina",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Proximamente: pedidos pendientes",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}
