package com.montse.apptransaccional

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.fragment.app.FragmentActivity
import com.montse.apptransaccional.core.navigation.NavigationWrapper
import com.montse.apptransaccional.ui.theme.AppTransaccionalTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTransaccionalTheme {
                NavigationWrapper()
            }
        }
    }
}
