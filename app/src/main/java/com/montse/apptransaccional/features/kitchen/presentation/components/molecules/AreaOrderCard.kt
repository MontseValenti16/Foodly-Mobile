package com.montse.apptransaccional.features.kitchen.presentation.components.molecules

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.montse.apptransaccional.core.data.remote.AreaItemDto

@Composable
fun AreaOrderCard(
    item: AreaItemDto,
    accentColor: Color,
    onMarkPreparing: () -> Unit,
    onMarkReady: () -> Unit
) {
    val isPending = item.status.lowercase() == "pending"
    val isPreparing = item.status.lowercase() == "preparing"

    val borderColor = if (isPending) Color(0xFFFF9800) else accentColor
    val backgroundColor = if (isPending) Color(0xFFFFF3E0) else Color(0xFFFBE9E7)
    val statusLabel = if (isPending) "Nuevo" else "Preparando"
    val statusColor = if (isPending) Color(0xFFFF9800) else accentColor

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        border = BorderStroke(1.5.dp, borderColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Top row: Mesa badge, status chip, waiter name
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Mesa badge
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = accentColor
                ) {
                    Text(
                        text = "Mesa ${item.tableNumber}",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                    )
                }

                // Status chip
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = statusColor.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = statusLabel,
                        color = statusColor,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }

                // Waiter name
                Text(
                    text = item.waiterName ?: "",
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Product info
            Text(
                text = "${item.quantity}x ${item.productName}",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color(0xFF212121)
            )

            // Notes
            if (!item.notes.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "\"${item.notes}\"",
                    fontSize = 13.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color(0xFF795548)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Action button
            if (isPending) {
                Button(
                    onClick = onMarkPreparing,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = accentColor)
                ) {
                    Icon(
                        imageVector = Icons.Default.LocalFireDepartment,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Empezar a Preparar", fontWeight = FontWeight.SemiBold)
                }
            } else if (isPreparing) {
                Button(
                    onClick = onMarkReady,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Marcar como Listo", fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}
