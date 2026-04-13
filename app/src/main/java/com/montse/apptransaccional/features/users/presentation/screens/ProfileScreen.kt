package com.montse.apptransaccional.features.users.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.montse.apptransaccional.features.dashboard.presentation.components.BottomNavigationBar
import com.montse.apptransaccional.features.dashboard.presentation.components.DashboardHeader
import com.montse.apptransaccional.features.users.presentation.viewmodels.ProfileViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onNavigate: (String) -> Unit,
    currentRoute: String,
    onLogout: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val foodlyPink = Color(0xFFE91E63)

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
                .background(Color.White)
        ) {
            DashboardHeader(onCreate = {})

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = foodlyPink
                    )
                } else if (state.error != null) {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = state.error!!, color = Color.Red)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { viewModel.loadUserProfile() },
                            colors = ButtonDefaults.buttonColors(containerColor = foodlyPink)
                        ) {
                            Text("Reintentar")
                        }
                    }
                } else {
                    state.user?.let { user ->
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Mi Perfil",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(vertical = 16.dp)
                                )
                                
                                IconButton(onClick = {
                                    viewModel.logout()
                                    onLogout()
                                }) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.Logout,
                                        contentDescription = "Cerrar Sesión",
                                        tint = foodlyPink
                                    )
                                }
                            }

                            ProfileItemCard(label = "Nombre Completo", value = user.name)
                            ProfileItemCard(label = "Nombre de Usuario", value = "@${user.username}")
                            ProfileItemCard(label = "Correo Electrónico", value = user.email ?: "No especificado")
                            ProfileItemCard(label = "Rol", value = user.role)
                            if (user.areaName != null) {
                                ProfileItemCard(label = "Área", value = user.areaName)
                            }
                            ProfileItemCard(
                                label = "Estado de Cuenta",
                                value = if (user.isActive) "Activa" else "Inactiva",
                                valueColor = if (user.isActive) Color(0xFF2ECC71) else Color.Red
                            )
                            
                            Spacer(modifier = Modifier.weight(1f))
                            
                            Button(
                                onClick = {
                                    viewModel.logout()
                                    onLogout()
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = foodlyPink),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("Cerrar Sesión", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileItemCard(
    label: String,
    value: String,
    valueColor: Color = Color.Black
) {
    val foodlyPink = Color(0xFFE91E63)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, foodlyPink.copy(alpha = 0.2f)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = label,
                fontSize = 12.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = value,
                fontSize = 16.sp,
                color = valueColor,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
