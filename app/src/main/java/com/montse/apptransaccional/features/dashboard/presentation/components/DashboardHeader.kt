package com.montse.apptransaccional.features.dashboard.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.montse.apptransaccional.R

@Composable
fun DashboardHeader(onCreate: () -> Unit) {
    val foodlyPink = Color(0xFFE91E63)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(foodlyPink, shape = MaterialTheme.shapes.large)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Card(
                shape = MaterialTheme.shapes.small,
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_foodly),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(50.dp)
                        .padding(1.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Foodly",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        IconButton(onClick = onCreate) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Crear",
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}
