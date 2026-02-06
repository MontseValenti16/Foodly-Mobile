package com.montse.apptransaccional.features.auth.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.montse.apptransaccional.R
import com.montse.apptransaccional.features.auth.presentation.viewmodels.AuthViewModel
import com.montse.apptransaccional.features.auth.presentation.viewmodels.AuthViewModelFactory

@Composable
fun LoginScreen(
    factory: AuthViewModelFactory,
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    val viewModel: AuthViewModel = viewModel(factory = factory)
    val state = viewModel.state

    // Tu color Rosa personalizado
    val FoodlyPink = Color(0xFFE91E63)

    // Estado para controlar si se ve la contraseña
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        // 1. LOGO GRANDE (Imagen llenando el círculo)
        Surface(
            modifier = Modifier
                .size(180.dp)
                .padding(4.dp),
            shape = CircleShape,
            shadowElevation = 10.dp,
            color = Color.White
        ) {
            Box(contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = R.drawable.logo_foodly),
                    contentDescription = "Logo Foodly",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 2. TEXTOS DE BIENVENIDA
        Text(
            text = "Welcome Back",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = "Sign in to continue",
            color = Color.Gray,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        // 3. CAMPO EMAIL (Letra Negra)
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.email,
            onValueChange = { viewModel.state = state.copy(email = it) },
            label = { Text("Email Address") },
            placeholder = { Text("ejemplo@correo.com") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = FoodlyPink) },
            shape = RoundedCornerShape(15.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = FoodlyPink,
                focusedLabelColor = FoodlyPink,
                cursorColor = FoodlyPink,
                focusedTextColor = Color.Black,   // Letra negra al escribir
                unfocusedTextColor = Color.Black  // Letra negra al terminar
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(15.dp))

        // 4. CAMPO PASSWORD (Ojito funcional + Letra Negra)
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.password,
            onValueChange = { viewModel.state = state.copy(password = it) },
            label = { Text("Password") },
            placeholder = { Text("••••••") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = FoodlyPink) },

            // ÍCONO DEL OJITO (Trailing Icon)
            trailingIcon = {
                // Usamos siempre el mismo ícono para no tener errores de librería
                val image = Icons.Default.Visibility

                // Si está visible = Rosa. Si está oculta = Gris.
                val tint = if (passwordVisible) FoodlyPink else Color.Gray
                val description = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = description, tint = tint)
                }
            },

            // Transformación visual (Puntos vs Texto normal)
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),

            shape = RoundedCornerShape(15.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = FoodlyPink,
                focusedLabelColor = FoodlyPink,
                cursorColor = FoodlyPink,
                focusedTextColor = Color.Black,   // Letra negra al escribir
                unfocusedTextColor = Color.Black  // Letra negra al terminar
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
            singleLine = true
        )

        // Manejo de Errores
        if (state.error != null) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(state.error!!, color = Color.Red, fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(30.dp))

        // 5. BOTÓN LOGIN
        if (state.isLoading) {
            CircularProgressIndicator(color = FoodlyPink)
        } else {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                onClick = { viewModel.login(onLoginSuccess) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = FoodlyPink,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(15.dp)
            ) {
                Text("Login", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 6. LINK A REGISTRO
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Don't have an account? ", color = Color.Gray)
            Text(
                text = "Sign up",
                color = FoodlyPink,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onNavigateToRegister() }
            )
        }
    }
}