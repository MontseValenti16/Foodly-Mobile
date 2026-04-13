package com.montse.apptransaccional.features.dashboard.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.montse.apptransaccional.R
import com.montse.apptransaccional.features.dashboard.domain.models.Dish

@Composable
fun DishCard(
    dish: Dish,
    onEdit: (Int) -> Unit,
    onDelete: (Int) -> Unit,
    accentColor: Color = Color(0xFFE91E63)
) {
    val availableColor = if (dish.disponible) Color(0xFF2ECC71) else Color(0xFFB0B0B0)
    val baseUrl = "https://foodly.mangelg.space"
    val fullImageUrl = if (dish.imageUrl != null) "$baseUrl${dish.imageUrl}" else null

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, accentColor.copy(alpha = 0.5f)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column {
            // Image Section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                if (fullImageUrl != null) {
                    AsyncImage(
                        model = fullImageUrl,
                        contentDescription = dish.nombre,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.logo_foodly),
                        contentDescription = dish.nombre,
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }

            // Content Section
            Column(modifier = Modifier.padding(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = dish.nombre,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color.Black,
                        maxLines = 1
                    )
                    
                    // Stock badge
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .background(Color(0xFFF0FDF4), shape = RoundedCornerShape(8.dp))
                            .padding(horizontal = 4.dp, vertical = 2.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .background(availableColor, shape = CircleShape)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (dish.disponible) "Stock" else "No",
                            fontSize = 8.sp,
                            color = Color.DarkGray
                        )
                    }
                }

                // Category tag
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFFDEFF4),
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Text(
                        text = "• ${dish.categoria ?: "General"}",
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        fontSize = 9.sp,
                        color = Color.Gray
                    )
                }

                Text(
                    text = "$${dish.precio}",
                    color = accentColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = { onEdit(dish.id) },
                        modifier = Modifier.size(30.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Edit,
                            contentDescription = "Edit",
                            tint = accentColor,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    IconButton(
                        onClick = { onDelete(dish.id) },
                        modifier = Modifier.size(30.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = "Delete",
                            tint = accentColor,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}
