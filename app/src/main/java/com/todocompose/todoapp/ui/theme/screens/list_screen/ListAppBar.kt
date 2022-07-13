package com.todocompose.todoapp.ui.theme.screens.list_screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.insets.ui.TopAppBar
import com.todocompose.todoapp.componets.DisplayAlertDialog
import com.todocompose.todoapp.componets.PriorityItem
import com.todocompose.todoapp.data.models.Priority
import com.todocompose.todoapp.ui.theme.LARGE_PADDING
import com.todocompose.todoapp.ui.theme.TOP_APP_BAR_HEIGHT
import com.todocompose.todoapp.ui.theme.TopAppBarBackground
import com.todocompose.todoapp.ui.theme.TopAppBarContentColor
import com.todocompose.todoapp.ui.theme.view_models.SharedViewModel
import com.todocompose.todoapp.util.Action
import com.todocompose.todoapp.util.SearchAppBarState
import com.todocompose.todoapp.util.TrailingIconState

@Composable
fun ListAppBar(
    sharedViewModel: SharedViewModel,
    searchAppBarState: SearchAppBarState,
    searchTextState: String
) {
    when (searchAppBarState) {
        SearchAppBarState.CLOSED -> {

            DefaultListAppBar(
                onSearchClicked = {
                    sharedViewModel.searchAppBarState.value = SearchAppBarState.OPENED
                },
                onSortClicked = { sharedViewModel.persistSortState(it) },
                onDeleteAllClicked = { sharedViewModel.action.value = Action.DELETE_ALL })
        }
        else -> {
            SearchAppBar(
                text = searchTextState,
                onTextChange = { sharedViewModel.searchTextState.value = it },
                onCloseClicked = {
                    sharedViewModel.searchAppBarState.value = SearchAppBarState.CLOSED
                    sharedViewModel.searchTextState.value = ""
                },
                onSearchClicked = {
                    sharedViewModel.searchDatabase(it)

                }
            )
        }

    }


}

@Composable
fun DefaultListAppBar(
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteAllClicked: () -> Unit
) {

    TopAppBar(
        modifier = Modifier
            .fillMaxWidth(),
        title = {
            Text(
                text = "Tasks",
                color = MaterialTheme.colors.TopAppBarContentColor,
            )
        },
        backgroundColor = MaterialTheme.colors.TopAppBarBackground,
        actions = {
            ListAppBarActions(
                onSearchClicked = onSearchClicked,
                onSortClicked = onSortClicked,
                onDeleteAllClicked = onDeleteAllClicked
            )
        }
    )


}

@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit
) {

    var trailingIconState by remember { mutableStateOf(TrailingIconState.READY_TO_DELETE) }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(TOP_APP_BAR_HEIGHT),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = MaterialTheme.colors.TopAppBarBackground
    ) {

        TextField(
            value = text,
            onValueChange = { onTextChange(it) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = "Search",
                    color = Color.White, modifier = Modifier.alpha(ContentAlpha.medium)
                )
            },
            singleLine = true,
            textStyle = TextStyle(
                color = MaterialTheme.colors.TopAppBarContentColor,
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),
            leadingIcon = {
                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.alpha(ContentAlpha.disabled)
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = MaterialTheme.colors.TopAppBarContentColor
                    )
                }
            },
            trailingIcon = {
                IconButton(onClick = {
                    when (trailingIconState) {
                        TrailingIconState.READY_TO_DELETE -> {
                            if (text.isEmpty()) {
                                onCloseClicked()
                            }
                            onTextChange("")
                            trailingIconState = TrailingIconState.READY_TO_CLOSE
                        }
                        TrailingIconState.READY_TO_CLOSE -> {
                            if (text.isNotEmpty()) {
                                onTextChange("")
                            } else {
                                onCloseClicked()
                                trailingIconState = TrailingIconState.READY_TO_DELETE
                            }

                        }
                    }
                })

                {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Dismiss Search",
                        tint = MaterialTheme.colors.TopAppBarContentColor
                    )

                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onSearchClicked(text) }),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = MaterialTheme.colors.TopAppBarContentColor,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.Transparent
            )
        )


    }

}

@Composable
fun ListAppBarActions(
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteAllClicked: () -> Unit
) {
    var openDialog by remember { mutableStateOf(false) }
    DisplayAlertDialog(
        title = "DeleteAll",
        message = "Are you sure ?",
        openDialog = openDialog,
        closeDialog = { openDialog = false },
        onYesClicked = { onDeleteAllClicked() })


    SearchAction(onSearchClicked)
    SortAction(onSortClicked = onSortClicked)
    DeleteAllAction(onDeleteAllClicked = { openDialog = true })

}

@Composable
fun SearchAction(onSearchClicked: () -> Unit) {
    IconButton(onClick = { onSearchClicked() }) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search",
            tint = MaterialTheme.colors.TopAppBarContentColor
        )
    }

}

@Composable
fun SortAction(
    onSortClicked: (Priority) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(
        onClick = { expanded = true }
    ) {
        Icon(
            imageVector = Icons.Default.Sort,
            contentDescription = "Sort",
            tint = MaterialTheme.colors.TopAppBarContentColor
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onSortClicked(Priority.LOW)
                }
            ) {
                PriorityItem(priority = Priority.LOW)
            }
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onSortClicked(Priority.HIGH)
                }
            ) {
                PriorityItem(priority = Priority.HIGH)
            }
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onSortClicked(Priority.NONE)
                }
            ) {
                PriorityItem(priority = Priority.NONE)
            }
        }
    }
}

@Composable
fun DeleteAllAction(onDeleteAllClicked: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    IconButton(onClick = { expanded = true }) {
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = "Delete All Tasks",
            tint = MaterialTheme.colors.TopAppBarContentColor
        )

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {

            DropdownMenuItem(onClick = {
                expanded = false
                onDeleteAllClicked()
            }) {

                Text(
                    text = "Delete All",
                    style = MaterialTheme.typography.subtitle2,
                    modifier = Modifier.padding(start = LARGE_PADDING)
                )

            }


        }

    }
}


@Preview
@Composable
fun MyPreview() {
    DefaultListAppBar({}, {}, {})

}

@Preview
@Composable
fun SearchAppBarPreview() {
    SearchAppBar(text = "Title", onTextChange = {}, onCloseClicked = { /*TODO*/ }) {

    }

}



