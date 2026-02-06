package com.montse.apptransaccional

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.montse.apptransaccional.core.di.AppContainer
import com.montse.apptransaccional.core.navigation.NavigationWrapper
import com.montse.apptransaccional.features.auth.di.AuthModule
import com.montse.apptransaccional.features.dashboard.di.DashboardModule
import com.montse.apptransaccional.ui.theme.AppTransaccionalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appContainer = AppContainer(this)
        val authModule = AuthModule(appContainer)
        val dashboardModule = DashboardModule(appContainer)

        setContent {
            AppTransaccionalTheme {
                NavigationWrapper(authModule, dashboardModule)
            }
        }
    }
}
