package com.montse.apptransaccional.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.montse.apptransaccional.features.auth.presentation.screens.LoginScreen
import com.montse.apptransaccional.features.auth.presentation.screens.RegisterScreen
import com.montse.apptransaccional.features.dashboard.presentation.screens.CreateDishScreen
import com.montse.apptransaccional.features.dashboard.presentation.screens.DishListScreen
import com.montse.apptransaccional.features.dashboard.presentation.screens.EditDishScreen

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Login) {

        composable<Screen.Login> {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Dashboard) {
                        popUpTo(Screen.Login) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register)
                }
            )
        }


        composable<Screen.Dashboard> {
            DishListScreen(
                onCreate = { navController.navigate(Screen.CreateDish) },
                onEdit = { id -> navController.navigate(Screen.EditDish(id)) },
                onDelete = {}
            )
        }

        composable<Screen.CreateDish> {
            CreateDishScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable<Screen.EditDish> { backStackEntry ->
            val editDish: Screen.EditDish = backStackEntry.toRoute()
            EditDishScreen(
                dishId = editDish.id,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
