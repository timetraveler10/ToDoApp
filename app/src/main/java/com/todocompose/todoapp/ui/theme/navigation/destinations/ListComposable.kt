package com.todocompose.todoapp.ui.theme.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.todocompose.todoapp.ui.theme.screens.list_screen.ListScreen
import com.todocompose.todoapp.ui.theme.view_models.SharedViewModel
import com.todocompose.todoapp.util.Constants.LIST_ARG_KEY
import com.todocompose.todoapp.util.Constants.LIST_SCREEN
import com.todocompose.todoapp.util.toAction

@ExperimentalAnimationApi
@ExperimentalMaterialApi
fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (Int) -> Unit,
    sharedViewModel: SharedViewModel
) {
    composable(LIST_SCREEN, arguments = listOf(navArgument(LIST_ARG_KEY) {
        type = NavType.StringType
    })) {

        val action = it.arguments?.getString(LIST_ARG_KEY).toAction()

        LaunchedEffect(key1 = action) {
            sharedViewModel.action.value = action
        }
        ListScreen(navigateToTaskScreen = navigateToTaskScreen, sharedViewModel = sharedViewModel)
    }
}