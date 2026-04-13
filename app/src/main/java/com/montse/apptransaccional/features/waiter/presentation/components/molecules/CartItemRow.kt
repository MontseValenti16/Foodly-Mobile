package com.montse.apptransaccional.features.waiter.presentation.components.molecules

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.montse.apptransaccional.features.waiter.domain.models.CartItem

@Composable
fun CartItemRow(
    item: CartItem,
    onAdd: () -> Unit,
    onRemove: () -> Unit,
    accentColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.name,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )
            Text(
                text = "$${String.format("%.2f", item.price * item.quantity)}",
                fontSize = 13.sp,
                color = Color.Gray
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            IconButton(
                onClick = onRemove,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = if (item.quantity == 1) Icons.Default.Delete else Icons.Default.Remove,
                    contentDescription = if (item.quantity == 1) "Eliminar" else "Reducir",
                    tint = if (item.quantity == 1) Color.Red else accentColor,
                    modifier = Modifier.size(18.dp)
                )
            }
            Text(
                text = "${item.quantity}",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            IconButton(
                onClick = onAdd,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar",
                    tint = accentColor,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}
