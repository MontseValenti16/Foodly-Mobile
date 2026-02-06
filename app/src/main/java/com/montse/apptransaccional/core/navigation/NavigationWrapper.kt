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
                    // CAMBIO: Al registrarse, ahora te manda al LOGIN para que entres
                    navController.navigate("login") {
                        // Borramos el registro de la pila para que "atrás" no vuelva al registro
                        popUpTo("register") { inclusive = true }
                    }
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

    }
}