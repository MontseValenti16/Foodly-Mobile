package com.montse.apptransaccional.features.dashboard.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.montse.apptransaccional.features.dashboard.presentation.viewmodels.DashboardViewModel

@Composable
fun DishForm(
    title: String,
    viewModel: DashboardViewModel,
    onLoad: () -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit
) {
    val state = viewModel.state
    val foodlyPink = Color(0xFFE91E63)
    val inputShape = RoundedCornerShape(15.dp)
    var nombreTouched by remember { mutableStateOf(false) }
    var precioTouched by remember { mutableStateOf(false) }
    var attemptedSubmit by remember { mutableStateOf(false) }

    val nombreError = if (state.nombre.trim().isEmpty()) "El nombre es obligatorio" else null
    val precioValue = state.precio.trim().toDoubleOrNull()
    val precioError = when {
        state.precio.trim().isEmpty() -> "El precio es obligatorio"
        precioValue == null -> "El precio debe ser un numero valido"
        precioValue <= 0.0 -> "El precio debe ser mayor a 0"
        else -> null
    }
    val shouldShowNombreError = (nombreTouched || attemptedSubmit) && nombreError != null
    val shouldShowPrecioError = (precioTouched || attemptedSubmit) && precioError != null
    val isFormValid = nombreError == null && precioError == null

    LaunchedEffect(Unit) {
        onLoad()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.nombre,
            onValueChange = {
                if (!nombreTouched) nombreTouched = true
                viewModel.onNombreChange(it)
            },
            label = { Text("Nombre") },
            isError = shouldShowNombreError,
            shape = inputShape,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = foodlyPink,
                focusedLabelColor = foodlyPink,
                cursorColor = foodlyPink,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            supportingText = {
                if (shouldShowNombreError) {
                    Text(text = nombreError ?: "", color = Color.Red)
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.descripcion,
            onValueChange = viewModel::onDescripcionChange,
            label = { Text("Descripcion") },
            shape = inputShape,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = foodlyPink,
                focusedLabelColor = foodlyPink,
                cursorColor = foodlyPink,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.precio,
            onValueChange = {
                if (!precioTouched) precioTouched = true
                viewModel.onPrecioChange(it)
            },
            label = { Text("Precio") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = shouldShowPrecioError,
            shape = inputShape,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = foodlyPink,
                focusedLabelColor = foodlyPink,
                cursorColor = foodlyPink,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            supportingText = {
                if (shouldShowPrecioError) {
                    Text(text = precioError ?: "", color = Color.Red)
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.categoria,
            onValueChange = viewModel::onCategoriaChange,
            label = { Text("Categoria") },
            shape = inputShape,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = foodlyPink,
                focusedLabelColor = foodlyPink,
                cursorColor = foodlyPink,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Disponible")
            Spacer(modifier = Modifier.width(8.dp))
            Switch(
                checked = state.disponible,
                onCheckedChange = viewModel::onDisponibleChange
            )
        }

        if (state.error != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = state.error, color = Color.Red)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Button(
                onClick = {
                    if (!isFormValid) {
                        attemptedSubmit = true
                        return@Button
                    }
                    onSave()
                },
                enabled = !state.isLoading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = foodlyPink,
                    contentColor = Color.White
                ),
                shape = inputShape
            ) {
                Text(text = "Guardar", fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedButton(
                onClick = onCancel,
                enabled = !state.isLoading,
                shape = inputShape
            ) {
                Text(text = "Cancelar", color = foodlyPink, fontWeight = FontWeight.Bold)
            }
        }

        if (state.isLoading) {
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator()
        }
    }
}
