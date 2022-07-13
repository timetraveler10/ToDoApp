package com.todocompose.todoapp.ui.theme.navigation

import androidx.navigation.NavController
import com.todocompose.todoapp.util.Action
import com.todocompose.todoapp.util.Constants.LIST_SCREEN
import com.todocompose.todoapp.util.Constants.SPLASH_SCREEN

class Screens(navController: NavController) {
    val splash: () -> Unit =
        {
            navController.navigate(route = "list/${Action.NO_ACTION}") {
                popUpTo(SPLASH_SCREEN) {
                    inclusive = true
                }
            }
        }

    val list: (Action) -> Unit = {

        navController.navigate("list/${it.name}") {
            popUpTo(LIST_SCREEN) {
                inclusive = true
            }
        }


    }
    val task: (Int) -> Unit = {
        navController.navigate("task/$it")
    }
}
