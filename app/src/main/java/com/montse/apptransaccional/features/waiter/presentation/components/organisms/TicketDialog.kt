package com.montse.apptransaccional.features.waiter.presentation.components.organisms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.montse.apptransaccional.features.waiter.data.datasources.remote.TicketDto
import com.montse.apptransaccional.features.waiter.presentation.components.atoms.TicketRow

@Composable
fun TicketDialog(
    ticket: TicketDto,
    tableNumber: Int,
    onDismiss: () -> Unit,
    accentColor: Color
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header
                Text(
                    text = "TICKET",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    letterSpacing = 2.sp
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Mesa $tableNumber",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                if (ticket.waiterName != null) {
                    Text(
                        text = "Mesero: ${ticket.waiterName}",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }

                Spacer(Modifier.height(12.dp))
                HorizontalDivider()
                Spacer(Modifier.height(12.dp))

                // Items
                ticket.items?.forEach { item ->
                    TicketRow(
                        label = "${item.quantity}x ${item.productName}",
                        value = "$${String.format("%.2f", item.subtotal.toDoubleOrNull() ?: 0.0)}"
                    )
                }

                Spacer(Modifier.height(12.dp))
                HorizontalDivider()
                Spacer(Modifier.height(12.dp))

                // Totals
                val subtotal = ticket.subtotal.toDoubleOrNull() ?: 0.0
                val discountAmount = ticket.discount.toDoubleOrNull() ?: 0.0
                val tipAmount = ticket.tip.toDoubleOrNull() ?: 0.0
                val total = ticket.total.toDoubleOrNull() ?: 0.0

                TicketRow(label = "Subtotal", value = "$${String.format("%.2f", subtotal)}")
                if (discountAmount > 0) {
                    TicketRow(
                        label = "Descuento",
                        value = "-$${String.format("%.2f", discountAmount)}"
                    )
                }
                if (tipAmount > 0) {
                    TicketRow(
                        label = "Propina",
                        value = "+$${String.format("%.2f", tipAmount)}"
                    )
                }

                Spacer(Modifier.height(8.dp))
                HorizontalDivider(thickness = 2.dp)
                Spacer(Modifier.height(8.dp))

                // Grand total
                Text(
                    text = "TOTAL: $${String.format("%.2f", total)}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(8.dp))

                // Payment method
                val paymentLabel = when (ticket.paymentMethod) {
                    "cash" -> "Efectivo"
                    "card" -> "Tarjeta"
                    "transfer" -> "Transferencia"
                    else -> ticket.paymentMethod
                }
                Text(
                    text = "Pago: $paymentLabel",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(20.dp))

                // Dismiss button
                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = accentColor)
                ) {
                    Text("Cerrar", color = Color.White)
                }
            }
        }
    }
}
