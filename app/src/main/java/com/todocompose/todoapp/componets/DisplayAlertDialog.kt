package com.todocompose.todoapp.componets

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight

@Composable
fun DisplayAlertDialog(
    title: String,
    message: String,
    openDialog: Boolean,
    closeDialog: () -> Unit,
    onYesClicked: () -> Unit
) {
    if (openDialog) {

        AlertDialog(
            onDismissRequest = { closeDialog() },
            confirmButton = {
                Button(onClick = {
                    onYesClicked()
                    closeDialog()
                },
                    content = { Text(text = "Yes") })
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { closeDialog() },
                    content = { Text(text = "No") })
            },
            text = {
                Text(
                    text = message,
                    fontSize = MaterialTheme.typography.subtitle1.fontSize,
                    fontWeight = FontWeight.Normal
                )
            },
            title = {
                Text(
                    text = title,
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    fontWeight = FontWeight.Bold
                )
            }


        )


    }

}