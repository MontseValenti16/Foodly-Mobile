package com.montse.apptransaccional.features.waiter.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.montse.apptransaccional.features.waiter.domain.models.TableStatus
import com.montse.apptransaccional.features.waiter.domain.models.WaiterTable

@Composable
fun WaiterTableCard(
    table: WaiterTable,
    onTap: () -> Unit
) {
    val isOccupied = table.status == TableStatus.OCUPADA

    val borderColor = if (isOccupied) Color(0xFFE91E63) else Color(0xFF4CAF50)
    val statusColor = if (isOccupied) Color(0xFFE91E63) else Color(0xFF4CAF50)
    val containerColor = if (isOccupied) Color(0xFFFCE4EC) else Color(0xFFE8F5E9)

    val capacityIcon = when {
        table.capacity <= 2 -> Icons.Outlined.Person
        table.capacity <= 4 -> Icons.Filled.Group
        else -> Icons.Filled.Groups
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { onTap() },
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.5.dp, borderColor.copy(alpha = 0.7f)),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                imageVector = capacityIcon,
                contentDescription = null,
                tint = borderColor,
                modifier = Modifier.size(36.dp)
            )

            Text(
                text = "Mesa ${table.number}",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black
            )

            Text(
                text = "${table.capacity} lugares",
                fontSize = 12.sp,
                color = Color.Gray
            )

            Surface(
                shape = RoundedCornerShape(20.dp),
                color = statusColor.copy(alpha = 0.15f)
            ) {
                Text(
                    text = table.status.label,
                    color = statusColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 4.dp)
                )
            }

            if (isOccupied && table.waiterName != null) {
                Text(
                    text = table.waiterName,
                    fontSize = 10.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
