package com.montse.apptransaccional.core.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.montse.apptransaccional.features.auth.presentation.screens.LoginScreen
import com.montse.apptransaccional.features.auth.presentation.screens.RegisterScreen
import com.montse.apptransaccional.features.dashboard.presentation.screens.*
import com.montse.apptransaccional.features.tables.presentation.screens.CreateTableScreen
import com.montse.apptransaccional.features.tables.presentation.screens.TablesScreen
import com.montse.apptransaccional.features.users.presentation.screens.CreateUserScreen
import com.montse.apptransaccional.features.users.presentation.screens.ProfileScreen
import com.montse.apptransaccional.features.users.presentation.screens.UsersManagementScreen
import com.montse.apptransaccional.features.users.presentation.viewmodels.ProfileViewModel
import com.montse.apptransaccional.features.waiter.presentation.screens.WaiterHomeScreen
import com.montse.apptransaccional.features.waiter.presentation.screens.WaiterSessionScreen
import com.montse.apptransaccional.features.kitchen.presentation.screens.KitchenHomeScreen
import com.montse.apptransaccional.features.bar.presentation.screens.BarHomeScreen

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route ?: "login"

    fun navigateByRole(role: String) {
        val destination = when (role) {
            "admin" -> "dashboard"
            "mesero" -> "waiter_home"
            "cocina" -> "kitchen_home"
            "barra" -> "bar_home"
            else -> "dashboard"
        }
        navController.navigate(destination) {
            popUpTo("login") { inclusive = true }
        }
    }

    NavHost(navController = navController, startDestination = "login") {

        composable("login") {
            LoginScreen(
                onLoginSuccess = { role -> navigateByRole(role) },
                onNavigateToRegister = {
                    navController.navigate("register")
                }
            )
        }

        composable("register") {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate("login") {
                        popUpTo("register") { inclusive = true }
                    }
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // ── Admin screens ──────────────────────────────────────────
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
                currentRoute = currentRoute,
                onCreate = { navController.navigate("tables/create") }
            )
        }

        composable("tables/create") {
            CreateTableScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable("profiles") {
            UsersManagementScreen(
                onNavigate = { route -> navController.navigate(route) },
                currentRoute = currentRoute,
                onCreate = { navController.navigate("profiles/create") }
            )
        }

        composable("profiles/create") {
            CreateUserScreen(
                onBack = { navController.popBackStack() }
            )
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

        // ── Mesero screens ─────────────────────────────────────────
        composable("waiter_home") {
            WaiterHomeScreen(
                onLogout = {
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onOpenSession = { sessionId, tableNumber ->
                    navController.navigate("waiter_session/$sessionId/$tableNumber")
                }
            )
        }

        composable("waiter_session/{sessionId}/{tableNumber}") {
            WaiterSessionScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // ── Cocina screens ─────────────────────────────────────────
        composable("kitchen_home") {
            KitchenHomeScreen(
                onLogout = {
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        // ── Barra screens ──────────────────────────────────────────
        composable("bar_home") {
            BarHomeScreen(
                onLogout = {
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}
