package com.todocompose.todoapp.ui.theme.navigation.destinations

import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.todocompose.todoapp.ui.theme.screens.splash.SplashScreen
import com.todocompose.todoapp.util.Constants

@ExperimentalMaterialApi
fun NavGraphBuilder.splashComposable(
    navigateToTaskScreen: () -> Unit){
    composable(route = Constants.SPLASH_SCREEN) {


        SplashScreen(navigateToListScreen = navigateToTaskScreen)
    }
}