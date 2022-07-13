package com.todocompose.todoapp.ui.theme.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.todocompose.todoapp.ui.theme.navigation.destinations.listComposable
import com.todocompose.todoapp.ui.theme.navigation.destinations.splashComposable
import com.todocompose.todoapp.ui.theme.navigation.destinations.taskComposable
import com.todocompose.todoapp.ui.theme.view_models.SharedViewModel
import com.todocompose.todoapp.util.Constants.SPLASH_SCREEN

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun SetupNavigation(navController: NavHostController, sharedViewModel: SharedViewModel) {

    val screen = remember(navController) {
        Screens(navController = navController)
    }
    NavHost(navController = navController, startDestination = SPLASH_SCREEN) {
        splashComposable (navigateToTaskScreen = screen.splash)
        listComposable(navigateToTaskScreen = screen.task, sharedViewModel = sharedViewModel)
        taskComposable(navigateToListScreen = screen.list, sharedViewModel)
    }


}