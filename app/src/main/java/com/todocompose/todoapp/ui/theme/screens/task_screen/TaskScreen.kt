package com.todocompose.todoapp.ui.theme.screens.task_screen

import android.content.Context
import android.widget.Toast
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.todocompose.todoapp.data.models.Priority
import com.todocompose.todoapp.data.models.ToDoTask
import com.todocompose.todoapp.ui.theme.view_models.SharedViewModel
import com.todocompose.todoapp.util.Action

@Composable
fun TaskScreen(
    navigateToListScreen: (Action) -> Unit,
    selectedTask: ToDoTask?,
    sharedViewModel: SharedViewModel
) {
    val context = LocalContext.current
    val title: String by sharedViewModel.title
    val description: String by sharedViewModel.description
    val priority: Priority by sharedViewModel.priority

    Scaffold(topBar = {
        TaskAppBar(
            navigateToListScreen = {
                if (it == Action.NO_ACTION) {
                    navigateToListScreen(it)
                } else {
                    if (sharedViewModel.validateFields()) {
                        navigateToListScreen(it)
                    } else {
                        context.toast("Fields Are Empty")
                    }
                }
            },
            selectedTask = selectedTask
        )
    }) {


        TaskContent(
            title = title,
            onTitleChange = { sharedViewModel.updateTitle(it) },
            description = description,
            onDescriptionChange = { sharedViewModel.description.value = it },
            priority = priority,
            onPrioritySelected = { sharedViewModel.priority.value = it }
        )


    }


}


fun Context.toast(title:String){
    Toast.makeText(this , title , Toast.LENGTH_SHORT).show()
}