package com.montse.apptransaccional.features.waiter.presentation.components.molecules

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.montse.apptransaccional.features.waiter.data.datasources.remote.OrderItemDto
import com.montse.apptransaccional.features.waiter.presentation.components.atoms.StatusDot

internal fun itemStatusColor(status: String): Color = when (status) {
    "pending" -> Color(0xFFFF9800)
    "preparing" -> Color(0xFF2196F3)
    "ready" -> Color(0xFF4CAF50)
    "delivered" -> Color(0xFF4CAF50)
    "cancelled" -> Color.Red
    else -> Color.Gray
}

internal fun itemStatusLabel(status: String): String = when (status) {
    "pending" -> "Pendiente"
    "preparing" -> "Preparando..."
    "ready" -> "Listo para entregar"
    "delivered" -> "Entregado"
    "cancelled" -> "Cancelado"
    else -> status
}

@Composable
fun OrderItemRow(
    item: OrderItemDto,
    onMarkDelivered: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        StatusDot(color = itemStatusColor(item.status))
        Text(
            text = "${item.quantity}x ${item.productName}",
            fontSize = 14.sp,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = itemStatusLabel(item.status),
            fontSize = 12.sp,
            color = itemStatusColor(item.status),
            fontWeight = FontWeight.Medium
        )
        when (item.status) {
            "ready" -> {
                FilledTonalButton(
                    onClick = onMarkDelivered,
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = Color(0xFF4CAF50).copy(alpha = 0.15f),
                        contentColor = Color(0xFF4CAF50)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Entregar",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text("Entregar", fontSize = 12.sp)
                }
            }
            "delivered" -> {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Entregado",
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
