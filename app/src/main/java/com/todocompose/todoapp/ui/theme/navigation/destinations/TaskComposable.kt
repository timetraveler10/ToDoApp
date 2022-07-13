package com.todocompose.todoapp.ui.theme.navigation.destinations

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.todocompose.todoapp.ui.theme.screens.task_screen.TaskScreen
import com.todocompose.todoapp.ui.theme.view_models.SharedViewModel
import com.todocompose.todoapp.util.Action
import com.todocompose.todoapp.util.Constants
import com.todocompose.todoapp.util.Constants.TASK_ARG_KEY

fun NavGraphBuilder.taskComposable(
    navigateToListScreen: (Action) -> Unit,
    sharedViewModel: SharedViewModel
) {
    composable(
        Constants.TASK_SCREEN,
        arguments = listOf(navArgument(TASK_ARG_KEY) {
            type = NavType.IntType
        })
    )
    {
        val taskId: Int = it.arguments!!.getInt(TASK_ARG_KEY)

        LaunchedEffect(key1 = taskId, block = { sharedViewModel.getSelectedTask(taskId = taskId) })
        val selectedTask by sharedViewModel.selectedTask.collectAsState()

        LaunchedEffect(key1 = taskId) {
            if (selectedTask != null || taskId == -1)
                sharedViewModel.updateTaskFields(selectedTask = selectedTask)
        }
        TaskScreen(
            navigateToListScreen = navigateToListScreen,
            selectedTask = selectedTask,
            sharedViewModel = sharedViewModel
        )
    }
}