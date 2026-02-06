package com.montse.apptransaccional

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.montse.apptransaccional.core.di.AppContainer
import com.montse.apptransaccional.core.navigation.NavigationWrapper
import com.montse.apptransaccional.features.auth.di.AuthModule
import com.montse.apptransaccional.features.menu.di.MenuModule
import com.montse.apptransaccional.ui.theme.AppTransaccionalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appContainer = AppContainer(this)
        val authModule = AuthModule(appContainer)
        val menuModule = MenuModule(appContainer)

        setContent {
            AppTransaccionalTheme {
                NavigationWrapper(authModule, menuModule)
            }
        }
    }
}
