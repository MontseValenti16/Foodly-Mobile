    package com.montse.apptransaccional.core.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.montse.apptransaccional.features.auth.di.AuthModule
import com.montse.apptransaccional.features.auth.presentation.screens.LoginScreen
import com.montse.apptransaccional.features.auth.presentation.screens.RegisterScreen
import com.montse.apptransaccional.features.dashboard.di.DashboardModule
import com.montse.apptransaccional.features.dashboard.presentation.screens.CreateDishScreen
import com.montse.apptransaccional.features.dashboard.presentation.screens.DishListScreen
import com.montse.apptransaccional.features.dashboard.presentation.screens.EditDishScreen
import com.montse.apptransaccional.features.dashboard.presentation.viewmodels.DashboardViewModel


@Composable
fun NavigationWrapper(
    authModule: AuthModule,
    dashboardModule: DashboardModule
) {
    val navController = rememberNavController()
    val dashboardViewModel: DashboardViewModel = viewModel(
        factory = dashboardModule.provideDashboardViewModelFactory()
    )

    NavHost(navController = navController, startDestination = "login") {

        composable("login") {
            LoginScreen(
                factory = authModule.provideAuthViewModelFactory(),
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
                factory = authModule.provideAuthViewModelFactory(),
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
                viewModel = dashboardViewModel,
                onCreate = { navController.navigate("dashboard/create") },
                onEdit = { id -> navController.navigate("dashboard/edit/$id") },
                onDelete = dashboardViewModel::deleteDish
            )
        }

        composable("dashboard/create") {
            CreateDishScreen(
                viewModel = dashboardViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable("dashboard/edit/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
                ?: return@composable
            EditDishScreen(
                viewModel = dashboardViewModel,
                dishId = id,
                onBack = { navController.popBackStack() }
            )
        }

    }
}