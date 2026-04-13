package com.montse.apptransaccional.features.waiter.presentation.components.organisms

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.montse.apptransaccional.features.waiter.data.datasources.remote.OrderDto
import com.montse.apptransaccional.features.waiter.data.datasources.remote.OrderItemDto
import com.montse.apptransaccional.features.waiter.presentation.components.molecules.OrderItemRow

@Composable
fun OrdersPanel(
    orders: List<OrderDto>,
    allItems: List<OrderItemDto>,
    allDelivered: Boolean,
    showExpanded: Boolean,
    onToggle: () -> Unit,
    onMarkDelivered: (Int) -> Unit,
    onCloseSession: () -> Unit,
    accentColor: Color
) {
    val deliveredCount = allItems.count { it.status == "delivered" }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Summary header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onToggle() },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Pedidos (${orders.size})",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "$deliveredCount/${allItems.size} entregados",
                        fontSize = 13.sp,
                        color = Color.Gray
                    )
                }
                Icon(
                    imageVector = if (showExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = if (showExpanded) "Colapsar" else "Expandir",
                    tint = accentColor
                )
            }

            // Expandable items list
            AnimatedVisibility(
                visible = showExpanded,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Column {
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                    allItems.forEach { item ->
                        OrderItemRow(
                            item = item,
                            onMarkDelivered = { onMarkDelivered(item.id) }
                        )
                    }
                }
            }

            // Close session button
            if (allDelivered && allItems.isNotEmpty()) {
                Spacer(Modifier.height(12.dp))
                Button(
                    onClick = onCloseSession,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = accentColor)
                ) {
                    Text("Cerrar cuenta", color = Color.White)
                }
            }
        }
    }
}
