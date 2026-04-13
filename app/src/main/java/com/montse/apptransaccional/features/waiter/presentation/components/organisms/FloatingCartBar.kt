package com.montse.apptransaccional.features.waiter.presentation.components.organisms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FloatingCartBar(
    itemCount: Int,
    total: Double,
    onShowCart: () -> Unit,
    accentColor: Color
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        color = accentColor,
        shadowElevation = 12.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$itemCount producto${if (itemCount != 1) "s" else ""}",
                color = Color.White,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )
            Text(
                text = "$${String.format("%.2f", total)}",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Button(
                onClick = onShowCart,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = accentColor
                )
            ) {
                Text("Ver Carrito", fontWeight = FontWeight.Bold)
            }
        }
    }
}
