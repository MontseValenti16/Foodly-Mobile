package com.montse.apptransaccional.features.waiter.presentation.components.atoms

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun StatusDot(color: Color, modifier: Modifier = Modifier) {
    Surface(modifier = modifier.size(10.dp), shape = CircleShape, color = color) {}
}
