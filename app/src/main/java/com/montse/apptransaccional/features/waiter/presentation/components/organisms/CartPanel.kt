package com.montse.apptransaccional.features.waiter.presentation.components.organisms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.montse.apptransaccional.features.dashboard.domain.models.Dish
import com.montse.apptransaccional.features.waiter.domain.models.CartItem
import com.montse.apptransaccional.features.waiter.presentation.components.molecules.CartItemRow

@Composable
fun CartPanel(
    cart: List<CartItem>,
    products: List<Dish>,
    cartTotal: Double,
    onAdd: (Dish) -> Unit,
    onRemove: (Int) -> Unit,
    onClear: () -> Unit,
    onSendOrder: () -> Unit,
    onClose: () -> Unit,
    accentColor: Color
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.55f),
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        color = Color.White,
        shadowElevation = 16.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Carrito",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    TextButton(onClick = onClear) {
                        Text("Limpiar", color = Color.Red)
                    }
                    TextButton(onClick = onClose) {
                        Text("Cerrar", color = Color.Gray)
                    }
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            // Cart items
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(cart) { cartItem ->
                    val dish = products.find { it.id == cartItem.productId }
                    CartItemRow(
                        item = cartItem,
                        onAdd = {
                            dish?.let { onAdd(it) }
                        },
                        onRemove = { onRemove(cartItem.productId) },
                        accentColor = accentColor
                    )
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            // Footer
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total: $${String.format("%.2f", cartTotal)}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Button(
                    onClick = onSendOrder,
                    enabled = cart.isNotEmpty(),
                    colors = ButtonDefaults.buttonColors(containerColor = accentColor)
                ) {
                    Text("Enviar Pedido", color = Color.White)
                }
            }
        }
    }
}
