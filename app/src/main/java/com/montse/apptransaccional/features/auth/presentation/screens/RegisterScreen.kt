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
import androidx.compose.material.icons.filled.Person
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
fun RegisterScreen(
    factory: AuthViewModelFactory,
    onRegisterSuccess: () -> Unit,
    onBack: () -> Unit
) {
    val viewModel: AuthViewModel = viewModel(factory = factory)
    val state = viewModel.state

    val FoodlyPink = Color(0xFFE91E63)
    var passwordVisible by remember { mutableStateOf(false) }
    var nameTouched by remember { mutableStateOf(false) }
    var emailTouched by remember { mutableStateOf(false) }
    var passwordTouched by remember { mutableStateOf(false) }
    var attemptedSubmit by remember { mutableStateOf(false) }

    val nameValue = state.name.trim()
    val emailValue = state.email.trim()
    val passwordValue = state.password
    val emailRegex = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$".toRegex()
    val nameError = when {
        nameValue.isEmpty() -> "El nombre es obligatorio"
        nameValue.length < 2 -> "El nombre debe tener al menos 2 caracteres"
        else -> null
    }
    val emailError = when {
        emailValue.isEmpty() -> "El correo es obligatorio"
        !emailRegex.matches(emailValue) -> "El correo no es valido"
        else -> null
    }
    val passwordError = when {
        passwordValue.isEmpty() -> "La contrasena es obligatoria"
        passwordValue.length < 6 -> "La contrasena debe tener al menos 6 caracteres"
        else -> null
    }
    val shouldShowNameError = (nameTouched || attemptedSubmit) && nameError != null
    val shouldShowEmailError = (emailTouched || attemptedSubmit) && emailError != null
    val shouldShowPasswordError = (passwordTouched || attemptedSubmit) && passwordError != null
    val isFormValid = nameError == null && emailError == null && passwordError == null

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Surface(
            modifier = Modifier.size(180.dp),
            shape = CircleShape,
            shadowElevation = 10.dp,
            color = Color.White
        ) {
            Box(contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = R.drawable.logo_foodly),
                    contentDescription = "Logo",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize().padding(4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("Create Account", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        Text("Sign up to get started", color = Color.Gray, fontSize = 16.sp)

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.name,
            onValueChange = {
                if (!nameTouched) nameTouched = true
                viewModel.state = state.copy(name = it)
            },
            label = { Text("Username") },
            placeholder = { Text("Tu Nombre") },
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = FoodlyPink) },
            shape = RoundedCornerShape(15.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = FoodlyPink,
                focusedLabelColor = FoodlyPink,
                cursorColor = FoodlyPink,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
            singleLine = true,
            isError = shouldShowNameError,
            supportingText = {
                if (shouldShowNameError) {
                    Text(text = nameError ?: "", color = Color.Red)
                }
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.email,
            onValueChange = {
                if (!emailTouched) emailTouched = true
                viewModel.state = state.copy(email = it)
            },
            label = { Text("Email Address") },
            placeholder = { Text("ejemplo@correo.com") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = FoodlyPink) },
            shape = RoundedCornerShape(15.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = FoodlyPink,
                focusedLabelColor = FoodlyPink,
                cursorColor = FoodlyPink,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
            singleLine = true,
            isError = shouldShowEmailError,
            supportingText = {
                if (shouldShowEmailError) {
                    Text(text = emailError ?: "", color = Color.Red)
                }
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.password,
            onValueChange = {
                if (!passwordTouched) passwordTouched = true
                viewModel.state = state.copy(password = it)
            },
            label = { Text("Password") },
            placeholder = { Text("••••••") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = FoodlyPink) },
            trailingIcon = {
                val image = Icons.Default.Visibility
                val tint = if (passwordVisible) FoodlyPink else Color.Gray
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = "Toggle Pass", tint = tint)
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            shape = RoundedCornerShape(15.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = FoodlyPink,
                focusedLabelColor = FoodlyPink,
                cursorColor = FoodlyPink,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
            singleLine = true,
            isError = shouldShowPasswordError,
            supportingText = {
                if (shouldShowPasswordError) {
                    Text(text = passwordError ?: "", color = Color.Red)
                }
            }
        )

        if (state.error != null) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(state.error!!, color = Color.Red, fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (state.isLoading) {
            CircularProgressIndicator(color = FoodlyPink)
        } else {
            Button(
                modifier = Modifier.fillMaxWidth().height(50.dp),
                onClick = {
                    if (!isFormValid) {
                        attemptedSubmit = true
                        return@Button
                    }
                    viewModel.register(onSuccess = onRegisterSuccess)
                },
                colors = ButtonDefaults.buttonColors(containerColor = FoodlyPink, contentColor = Color.White),
                shape = RoundedCornerShape(15.dp)
            ) {
                Text("Sign Up", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Already have an account? ", color = Color.Gray)
            Text(
                text = "Login",
                color = FoodlyPink,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onBack() }
            )
        }
    }
}