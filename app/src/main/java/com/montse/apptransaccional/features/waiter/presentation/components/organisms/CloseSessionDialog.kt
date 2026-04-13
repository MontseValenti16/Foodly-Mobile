package com.montse.apptransaccional.features.waiter.presentation.components.organisms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun CloseSessionDialog(
    paymentMethod: String,
    tip: String,
    discount: String,
    onPaymentMethodChange: (String) -> Unit,
    onTipChange: (String) -> Unit,
    onDiscountChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    accentColor: Color
) {
    val paymentMethods = listOf("cash", "card", "transfer")
    val paymentLabels = mapOf(
        "cash" to "Efectivo",
        "card" to "Tarjeta",
        "transfer" to "Transferencia"
    )

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                Text(
                    text = "Cerrar Cuenta",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

                Spacer(Modifier.height(16.dp))

                // Payment method chips
                Text(
                    text = "Metodo de pago",
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Spacer(Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    paymentMethods.forEach { method ->
                        FilterChip(
                            selected = paymentMethod == method,
                            onClick = { onPaymentMethodChange(method) },
                            label = { Text(paymentLabels[method] ?: method) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = accentColor.copy(alpha = 0.15f),
                                selectedLabelColor = accentColor
                            )
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Tip field
                OutlinedTextField(
                    value = tip,
                    onValueChange = onTipChange,
                    label = { Text("Propina (%)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )

                Spacer(Modifier.height(12.dp))

                // Discount field
                OutlinedTextField(
                    value = discount,
                    onValueChange = onDiscountChange,
                    label = { Text("Descuento (%)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )

                Spacer(Modifier.height(24.dp))

                // Action buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancelar", color = Color.Gray)
                    }
                    Button(
                        onClick = onConfirm,
                        colors = ButtonDefaults.buttonColors(containerColor = accentColor)
                    ) {
                        Text("Confirmar", color = Color.White)
                    }
                }
            }
        }
    }
}
