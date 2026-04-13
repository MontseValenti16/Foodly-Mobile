package com.montse.apptransaccional.features.waiter.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.montse.apptransaccional.features.dashboard.domain.models.Dish

@Composable
fun ProductCard(
    dish: Dish,
    onAdd: () -> Unit
) {
    val foodlyPink = Color(0xFFE91E63)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onAdd() },
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.5f)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = dish.nombre,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (dish.descripcion != null) {
                    Text(
                        text = dish.descripcion,
                        fontSize = 11.sp,
                        color = Color.Gray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Text(
                    text = "$${String.format("%.2f", dish.precio)}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = foodlyPink
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            FilledIconButton(
                onClick = onAdd,
                modifier = Modifier.size(36.dp),
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = foodlyPink,
                    contentColor = Color.White
                )
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar", modifier = Modifier.size(20.dp))
            }
        }
    }
}
