package com.montse.apptransaccional.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.montse.apptransaccional.features.auth.di.AuthModule
import com.montse.apptransaccional.features.auth.presentation.screens.LoginScreen
import com.montse.apptransaccional.features.auth.presentation.screens.RegisterScreen
import com.montse.apptransaccional.features.menu.di.MenuModule


@Composable
fun NavigationWrapper(
    authModule: AuthModule,
    menuModule: MenuModule
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {

        // --- RUTA: LOGIN ---
        composable("login") {
            LoginScreen(
                factory = authModule.provideAuthViewModelFactory(),
                onLoginSuccess = {
                    // Vamos al menú y borramos el Login del historial
                    navController.navigate("menu") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate("register")
                }
            )
        }

        // --- RUTA: REGISTRO ---
        composable("register") {
            RegisterScreen(
                factory = authModule.provideAuthViewModelFactory(),
                onRegisterSuccess = {
                    // Al registrarse, entra directo al menú
                    navController.navigate("menu") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onBack = {
                    navController.popBackStack() // Vuelve atrás (al Login)
                }
            )
        }

    }
}