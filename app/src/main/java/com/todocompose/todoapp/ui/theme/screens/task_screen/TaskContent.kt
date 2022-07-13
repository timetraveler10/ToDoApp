package com.todocompose.todoapp.ui.theme.screens.task_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.todocompose.todoapp.componets.PriorityDropDown
import com.todocompose.todoapp.data.models.Priority
import com.todocompose.todoapp.ui.theme.LARGE_PADDING
import com.todocompose.todoapp.ui.theme.MEDIUM_PADDING

@Composable
fun TaskContent(
    title: String,
    onTitleChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    priority: Priority,
    onPrioritySelected: (Priority) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(LARGE_PADDING)

    ) {

        OutlinedTextField(
            value = title,
            onValueChange = {
                onTitleChange(it)
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Title", style = MaterialTheme.typography.body1) },
            singleLine = true,
            shape = RoundedCornerShape(5.dp)
        )

        Divider(modifier = Modifier.height(MEDIUM_PADDING), color = MaterialTheme.colors.background)


        PriorityDropDown(priority = priority, onPrioritySelected = onPrioritySelected)

        OutlinedTextField(
            value = description,
            onValueChange = { onDescriptionChange(it) },
            modifier = Modifier.fillMaxSize(),
            label = { Text(text = "Description", style = MaterialTheme.typography.body1) },
            shape = RoundedCornerShape(5.dp))


    }

}