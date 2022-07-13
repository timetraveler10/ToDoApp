package com.todocompose.todoapp.ui.theme.screens.list_screen

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.todocompose.todoapp.ui.theme.FabBackgroundColor
import com.todocompose.todoapp.ui.theme.view_models.SharedViewModel
import com.todocompose.todoapp.util.Action
import com.todocompose.todoapp.util.SearchAppBarState
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun ListScreen(
    navigateToTaskScreen: (Int) -> Unit,
    sharedViewModel: SharedViewModel
) {

    LaunchedEffect(key1 = true) {
        sharedViewModel.getAllTasks()
        sharedViewModel.readSortState()
    }

    val action by sharedViewModel.action
    val allTasks by sharedViewModel.allTasks.collectAsState()
    val searchedTasks by sharedViewModel.searchedTasks.collectAsState()

    /***************************SORTING****************************/
    val sortState by sharedViewModel.sortState.collectAsState()
    val lowPriorityTasks by sharedViewModel.lowPriorityTasks.collectAsState()
    val highPriorityTasks by sharedViewModel.highPriorityTasks.collectAsState()


    /***************************SORTING****************************/

    val searchAppBarState: SearchAppBarState by sharedViewModel.searchAppBarState
    val searchTextState: String by sharedViewModel.searchTextState
    val scaffoldState = rememberScaffoldState()


    DisplaySnackBar(
        scaffoldState = scaffoldState,
        handleDatabaseActions = { sharedViewModel.handleDatabaseAction(action) },
        taskTitle = sharedViewModel.title.value,
        action = action,
        onUndoClicked = { sharedViewModel.action.value = it }
    )

    Scaffold(
        floatingActionButton = { ListFab(onFabClicked = navigateToTaskScreen) },
        topBar = {
            ListAppBar(
                sharedViewModel = sharedViewModel,
                searchAppBarState = searchAppBarState,
                searchTextState = searchTextState
            )

        }, scaffoldState = scaffoldState
    )
    {


        ListContent(
            allTasks = allTasks,
            navigateToTaskScreen = navigateToTaskScreen,
            searchedTasks = searchedTasks,
            searchedAppBarState = searchAppBarState,
            lowPriorityTasks = lowPriorityTasks,
            highPriorityTasks = highPriorityTasks,
            sortState = sortState,
            onSwipeToDismiss = { action, toDoTask ->
                sharedViewModel.action.value = action
                sharedViewModel.updateTaskFields(toDoTask)
            }
        )


    }


}


@Composable
fun ListFab(onFabClicked: (taskId: Int) -> Unit) {
    FloatingActionButton(
        onClick = { onFabClicked(-1) },
        backgroundColor = MaterialTheme.colors.FabBackgroundColor
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "Add", tint = Color.White)
    }
}

@Composable
fun DisplaySnackBar(
    scaffoldState: ScaffoldState,
    onUndoClicked: (Action) -> Unit,
    handleDatabaseActions: () -> Unit,
    taskTitle: String,
    action: Action
) {

    handleDatabaseActions()
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = action) {
        if (action != Action.NO_ACTION) {
            scope.launch {
                val snackBarResult =
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = setMessage(action, taskTitle),
                        actionLabel = setActionLabel(action = action)
                    )

                undoDeletedTask(
                    action = action,
                    snackBarResult = snackBarResult,
                    onUndoClicked = onUndoClicked
                )


            }
        }
    }

}

private fun setMessage(action: Action, taskTitle: String): String {
    return when (action) {

        Action.DELETE_ALL -> "All Tasks Removed"
        else -> {
            "${action.name} :$taskTitle"
        }
    }

}

private fun setActionLabel(action: Action): String = if (action.name == "DELETE") "Undo" else "Ok"

private fun undoDeletedTask(
    action: Action,
    snackBarResult: SnackbarResult,
    onUndoClicked: (Action) -> Unit
) {

    if (snackBarResult == SnackbarResult.ActionPerformed && action == Action.DELETE) {
        onUndoClicked(Action.UNDO)
    }

}