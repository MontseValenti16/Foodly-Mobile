package com.montse.apptransaccional.features.dashboard.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.montse.apptransaccional.features.dashboard.presentation.components.BottomNavigationBar
import com.montse.apptransaccional.features.dashboard.presentation.components.DashboardHeader

@Composable
fun TablesScreen(onNavigate: (String) -> Unit, currentRoute: String) {
    Scaffold(
        bottomBar = { BottomNavigationBar(currentRoute = currentRoute, onNavigate = onNavigate) }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).background(Color.White)) {
            DashboardHeader(onCreate = {})
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Gestión de Mesas")
            }
        }
    }
}
