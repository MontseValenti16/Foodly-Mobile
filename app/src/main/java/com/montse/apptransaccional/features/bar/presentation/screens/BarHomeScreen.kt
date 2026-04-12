package com.montse.apptransaccional.features.bar.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.LocalBar
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
import com.montse.apptransaccional.features.bar.presentation.viewmodels.BarViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarHomeScreen(
    onLogout: () -> Unit,
    viewModel: BarViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val barBlue = Color(0xFF2A7AE0)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Barra", fontWeight = FontWeight.Bold, fontSize = 18.sp)
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
                    containerColor = barBlue,
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
                    CircularProgressIndicator(color = barBlue)
                }
                state.error != null -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = state.error!!, color = Color.Red)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { viewModel.loadProfile() },
                            colors = ButtonDefaults.buttonColors(containerColor = barBlue)
                        ) {
                            Text("Reintentar")
                        }
                    }
                }
                else -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.LocalBar,
                            contentDescription = null,
                            modifier = Modifier.size(80.dp),
                            tint = barBlue
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Panel de Barra",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Proximamente: bebidas pendientes",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}
