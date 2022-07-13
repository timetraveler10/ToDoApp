package com.todocompose.todoapp.ui.theme.screens.task_screen

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.todocompose.todoapp.componets.DisplayAlertDialog
import com.todocompose.todoapp.data.models.Priority
import com.todocompose.todoapp.data.models.ToDoTask
import com.todocompose.todoapp.ui.theme.TopAppBarBackground
import com.todocompose.todoapp.ui.theme.TopAppBarContentColor
import com.todocompose.todoapp.util.Action

@Composable
fun TaskAppBar(navigateToListScreen: (Action) -> Unit, selectedTask: ToDoTask?) {

    if (selectedTask == null) {
        NewTaskAppBar(navigateToListScreen = navigateToListScreen)


    } else {
        ExistingTaskAppBar(
            navigateToListScreen = navigateToListScreen,
            selectedTask = selectedTask
        )
    }
}

@Composable
fun NewTaskAppBar(navigateToListScreen: (Action) -> Unit) {

    TopAppBar(
        navigationIcon = {
            BackAction(onBackClicked = navigateToListScreen)
        },
        title = { Text(text = "Add Task", color = MaterialTheme.colors.TopAppBarContentColor) },
        backgroundColor = MaterialTheme.colors.TopAppBarBackground,
        actions = {
            AddAction(onAddClicked = navigateToListScreen)
        }
    )


}


@Composable
fun ExistingTaskAppBar(navigateToListScreen: (Action) -> Unit, selectedTask: ToDoTask) {

    TopAppBar(
        navigationIcon = {
            CloseAction(onCloseClicked = navigateToListScreen)
        },
        title = {
            Text(
                text = selectedTask.title,
                color = MaterialTheme.colors.TopAppBarContentColor,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        },
        backgroundColor = MaterialTheme.colors.TopAppBarBackground,
        actions = {
            ExistingTaskAppBarActions(navigateToListScreen = navigateToListScreen, selectedTask =selectedTask )

        }
    )

}

@Composable
fun ExistingTaskAppBarActions(navigateToListScreen: (Action) -> Unit, selectedTask: ToDoTask) {

    var openDialog by remember { mutableStateOf(false) }
    DisplayAlertDialog(
        title = "Delete Task : ${selectedTask.title}",
        message = "Confirm Delete",
        openDialog = openDialog,
        closeDialog = { openDialog = false } ,
        onYesClicked = {navigateToListScreen(Action.DELETE)})
    UpdateTaskAction(onUpdateAction = navigateToListScreen)
    DeleteTaskAction(onDeleteClicked = { openDialog = true } )
}


@Composable
fun CloseAction(onCloseClicked: (Action) -> Unit) {
    IconButton(onClick = { onCloseClicked(Action.NO_ACTION) }) {

        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "close",
            tint = MaterialTheme.colors.TopAppBarContentColor
        )

    }


}

@Composable
fun DeleteTaskAction(onDeleteClicked: (Action) -> Unit) {
    IconButton(onClick = { onDeleteClicked(Action.DELETE) }) {

        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete",
            tint = MaterialTheme.colors.TopAppBarContentColor
        )

    }


}

@Composable
fun UpdateTaskAction(onUpdateAction: (Action) -> Unit) {
    IconButton(onClick = { onUpdateAction(Action.UPDATE) }) {

        Icon(
            imageVector = Icons.Default.Save,
            contentDescription = "close",
            tint = MaterialTheme.colors.TopAppBarContentColor
        )

    }


}

@Composable
fun AddAction(onAddClicked: (Action) -> Unit) {
    IconButton(onClick = { onAddClicked(Action.ADD) }) {

        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = "Add Task",
            tint = MaterialTheme.colors.TopAppBarContentColor
        )

    }


}

@Composable
fun BackAction(onBackClicked: (Action) -> Unit) {
    IconButton(onClick = { onBackClicked(Action.NO_ACTION) }) {

        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back To List Screen",
            tint = MaterialTheme.colors.TopAppBarContentColor
        )

    }


}

@Preview
@Composable
fun AppTaskBar() {
    ExistingTaskAppBar(
        navigateToListScreen = {},
        selectedTask = ToDoTask(0, "Title", "Description", Priority.HIGH)
    )


}