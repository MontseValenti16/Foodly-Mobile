package com.montse.apptransaccional.features.tables.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.montse.apptransaccional.features.tables.domain.models.Table

@Composable
fun TableCard(
    table: Table,
    onDelete: (Int) -> Unit,
    accentColor: Color = Color(0xFFE91E63)
) {
    val capacityIcon = when {
        table.capacity <= 2 -> Icons.Outlined.Person
        table.capacity <= 4 -> Icons.Filled.Group
        else -> Icons.Filled.Groups
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.5.dp, accentColor.copy(alpha = 0.6f)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                imageVector = capacityIcon,
                contentDescription = null,
                tint = Color.DarkGray,
                modifier = Modifier.size(40.dp)
            )

            Text(
                text = "Table ${table.number}",
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = Color.Black
            )

            Text(
                text = "${table.capacity} seats",
                fontSize = 12.sp,
                color = accentColor
            )

            IconButton(
                onClick = { onDelete(table.id) },
                modifier = Modifier.size(28.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "Eliminar mesa",
                    tint = Color.Gray,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}
