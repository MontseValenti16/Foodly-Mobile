package com.montse.apptransaccional.features.users.presentation.components

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
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.montse.apptransaccional.features.users.domain.models.User

@Composable
fun EmployeeCard(
    user: User,
    onDelete: (Int) -> Unit,
    accentColor: Color = Color(0xFFE91E63)
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.dp, accentColor.copy(alpha = 0.55f)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.PersonOutline,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(34.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = user.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        AssistChip(
                            onClick = {},
                            label = { Text(user.roleLabel, fontSize = 12.sp) },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = accentColor,
                                labelColor = Color.White
                            )
                        )
                        AssistChip(
                            onClick = {},
                            label = {
                                Text(
                                    text = if (user.isActive) "Activo" else "Inactivo",
                                    fontSize = 12.sp
                                )
                            },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = if (user.isActive) accentColor.copy(alpha = 0.15f) else Color.LightGray,
                                labelColor = if (user.isActive) accentColor else Color.DarkGray
                            )
                        )
                    }
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = "Editar",
                        tint = accentColor
                    )
                }
                IconButton(onClick = { onDelete(user.id) }) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = "Eliminar",
                        tint = accentColor
                    )
                }
            }
        }
    }
}
