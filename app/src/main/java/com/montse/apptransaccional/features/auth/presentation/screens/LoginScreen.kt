package com.montse.apptransaccional.features.auth.presentation.screens

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.montse.apptransaccional.R
import com.montse.apptransaccional.features.auth.presentation.viewmodels.AuthViewModel

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val state by viewModel.authState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val activity = context as? FragmentActivity
    val mainExecutor = remember(context) { ContextCompat.getMainExecutor(context) }

    LaunchedEffect(Unit) {
        viewModel.refreshBiometricAvailability()
    }

    val FoodlyPink = Color(0xFFE91E63)

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

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

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.username,
            onValueChange = {
                viewModel.onUsernameChange(it)
            },
            label = { Text("Username") },
            placeholder = { Text("carlos_test_01_01") },
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
            isError = state.shouldShowUsernameError,
            supportingText = {
                if (state.shouldShowUsernameError) {
                    Text(text = state.usernameError ?: "", color = Color.Red)
                }
            }
        )

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.password,
            onValueChange = {
                viewModel.onPasswordChange(it)
            },
            label = { Text("Password") },
            placeholder = { Text("••••••") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = FoodlyPink) },

            trailingIcon = {
                val image = Icons.Default.Visibility

                val tint = if (state.isPasswordVisible) FoodlyPink else Color.Gray
                val description = if (state.isPasswordVisible) "Ocultar contraseña" else "Mostrar contraseña"

                IconButton(onClick = { viewModel.togglePasswordVisibility() }) {
                    Icon(imageVector = image, contentDescription = description, tint = tint)
                }
            },

            visualTransformation = if (state.isPasswordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },

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
            isError = state.shouldShowPasswordError,
            supportingText = {
                if (state.shouldShowPasswordError) {
                    Text(text = state.passwordError ?: "", color = Color.Red)
                }
            }
        )

        if (state.error != null) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(state.error!!, color = Color.Red, fontSize = 14.sp)
        }

        if (state.biometricError != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(state.biometricError!!, color = Color.Red, fontSize = 13.sp)
        }

        Spacer(modifier = Modifier.height(30.dp))

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

            if (state.isBiometricLoginAvailable) {
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    onClick = {
                        if (activity == null) {
                            viewModel.onBiometricPromptError("No fue posible abrir el lector de huella")
                            return@OutlinedButton
                        }

                        val canAuthenticate = BiometricManager.from(context).canAuthenticate(
                            BiometricManager.Authenticators.BIOMETRIC_STRONG or
                                BiometricManager.Authenticators.BIOMETRIC_WEAK
                        )

                        if (canAuthenticate != BiometricManager.BIOMETRIC_SUCCESS) {
                            viewModel.onBiometricPromptError("La huella no esta disponible en este dispositivo")
                            return@OutlinedButton
                        }

                        val promptInfo = BiometricPrompt.PromptInfo.Builder()
                            .setTitle("Iniciar sesion con huella")
                            .setSubtitle("Confirma tu identidad para continuar")
                            .setNegativeButtonText("Cancelar")
                            .build()

                        val prompt = BiometricPrompt(
                            activity,
                            mainExecutor,
                            object : BiometricPrompt.AuthenticationCallback() {
                                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                                    super.onAuthenticationSucceeded(result)
                                    viewModel.loginWithBiometrics(onLoginSuccess)
                                }

                                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                                    super.onAuthenticationError(errorCode, errString)
                                    viewModel.onBiometricPromptError(errString.toString())
                                }

                                override fun onAuthenticationFailed() {
                                    super.onAuthenticationFailed()
                                    viewModel.onBiometricPromptError("Huella no reconocida")
                                }
                            }
                        )

                        prompt.authenticate(promptInfo)
                    },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = FoodlyPink),
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Fingerprint,
                        contentDescription = null,
                        tint = FoodlyPink
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Login con huella", fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

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