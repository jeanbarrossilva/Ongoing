package com.jeanbarrossilva.ongoing.platform.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun OngoingTheme(content: @Composable () -> Unit) {
    MaterialTheme(colors, typography = Typography) {
        val systemUiController = rememberSystemUiController()
        val statusBarColor = MaterialTheme.colorScheme.primaryContainer

        SideEffect {
            systemUiController.setStatusBarColor(statusBarColor)
        }

        content()
    }
}