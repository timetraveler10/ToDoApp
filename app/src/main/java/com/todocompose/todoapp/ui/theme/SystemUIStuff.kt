package com.example.todoapp.userui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.todocompose.todoapp.ui.theme.Purple500
import com.todocompose.todoapp.ui.theme.Purple700


@Composable
fun SystemUiController() {
    // Get the current SystemUiController
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = MaterialTheme.colors.isLight

    SideEffect {
        // Update all of the system bar colors to be transparent, and use
        // dark icons if we're in light theme
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = useDarkIcons ,
        )
        systemUiController.setStatusBarColor(if (useDarkIcons) Purple700 else Color.Black)
        systemUiController.navigationBarDarkContentEnabled = true
        // setStatusBarsColor() and setNavigationBarsColor() also exist
    }
}
