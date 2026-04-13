package com.montse.apptransaccional.features.dashboard.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BottomNavigationBar(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    userName: String = "Miguel G.",
    userRole: String = "Administrador"
) {
    val foodlyPink = Color(0xFFE91E63)
    val lightGray = Color(0xFFF2F2F2)
    val selectedGray = Color(0xFFD9D9D9)

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = lightGray,
        tonalElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Sales
            NavigationItem(
                modifier = Modifier.weight(1f),
                icon = Icons.Outlined.MonetizationOn,
                label = "Sales",
                isSelected = currentRoute == "sales",
                onClick = { onNavigate("sales") }
            )

            // Dishes (Selected by default/current)
            NavigationItem(
                modifier = Modifier.weight(1f).background(if (currentRoute == "dashboard") foodlyPink else Color.Transparent),
                icon = Icons.Outlined.SoupKitchen,
                label = "Dishes",
                isSelected = currentRoute == "dashboard",
                selectedColor = Color.White,
                unselectedColor = Color.DarkGray,
                onClick = { onNavigate("dashboard") }
            )

            // Tables
            NavigationItem(
                modifier = Modifier.weight(1f),
                icon = Icons.Outlined.TableBar,
                label = "Tables",
                isSelected = currentRoute == "tables",
                onClick = { onNavigate("tables") }
            )

            // Profiles
            NavigationItem(
                modifier = Modifier.weight(1f),
                icon = Icons.Outlined.Badge,
                label = "Profiles",
                isSelected = currentRoute == "profiles",
                onClick = { onNavigate("profiles") }
            )

            // User Profile Section
            Box(
                modifier = Modifier
                    .weight(1.5f)
                    .fillMaxHeight()
                    .clickable { onNavigate("profile") }
                    .padding(horizontal = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(horizontalAlignment = Alignment.End) {
                        Text(text = userName, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        Text(text = userRole, fontSize = 10.sp, color = Color.Gray)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.AccountBox,
                        contentDescription = "Profile",
                        modifier = Modifier.size(32.dp),
                        tint = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun NavigationItem(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    selectedColor: Color = Color.White,
    unselectedColor: Color = Color.DarkGray,
    onClick: () -> Unit
) {
    val contentColor = if (isSelected) selectedColor else unselectedColor
    
    Column(
        modifier = modifier
            .fillMaxHeight()
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = contentColor,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = label,
            color = contentColor,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}
