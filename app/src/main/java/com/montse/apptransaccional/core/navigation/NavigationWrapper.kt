package com.montse.apptransaccional.core.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.montse.apptransaccional.features.auth.presentation.screens.LoginScreen
import com.montse.apptransaccional.features.auth.presentation.screens.RegisterScreen
import com.montse.apptransaccional.features.dashboard.presentation.screens.*
import com.montse.apptransaccional.features.users.presentation.screens.ProfileScreen
import com.montse.apptransaccional.features.users.presentation.viewmodels.ProfileViewModel

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route ?: "login"

    NavHost(navController = navController, startDestination = "login") {

        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("dashboard") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate("register")
                }
            )
        }
        
        composable("register") {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate("dashboard") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("dashboard") {
            DishListScreen(
                onCreate = { navController.navigate("dashboard/create") },
                onEdit = { id -> navController.navigate("dashboard/edit/$id") },
                onNavigate = { route -> navController.navigate(route) },
                currentRoute = currentRoute
            )
        }

        composable("sales") {
            SalesScreen(
                onNavigate = { route -> navController.navigate(route) },
                currentRoute = currentRoute
            )
        }

        composable("tables") {
            TablesScreen(
                onNavigate = { route -> navController.navigate(route) },
                currentRoute = currentRoute
            )
        }

        composable("profiles") {
            // Aquí puedes implementar una pantalla similar para la gestión de usuarios
            Box { Text("Gestión de Usuarios") }
        }

        composable("dashboard/create") {
            CreateDishScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable("dashboard/edit/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
                ?: return@composable
            EditDishScreen(
                dishId = id,
                onBack = { navController.popBackStack() }
            )
        }

        composable("profile") {
            val viewModel: ProfileViewModel = hiltViewModel()
            ProfileScreen(
                viewModel = viewModel,
                onNavigate = { route -> navController.navigate(route) },
                currentRoute = currentRoute
            )
        }
    }
}
