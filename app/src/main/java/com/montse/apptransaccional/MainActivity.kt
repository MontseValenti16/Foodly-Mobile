package com.montse.apptransaccional

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.montse.apptransaccional.core.navigation.NavigationWrapper
import com.montse.apptransaccional.ui.theme.AppTransaccionalTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTransaccionalTheme {
                NavigationWrapper()
            }
        }
    }
}
