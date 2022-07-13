package com.todocompose.todoapp.ui.theme.screens.list_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.todocompose.todoapp.data.models.Priority
import com.todocompose.todoapp.data.models.ToDoTask
import com.todocompose.todoapp.ui.theme.*
import com.todocompose.todoapp.util.Action
import com.todocompose.todoapp.util.RequestState
import com.todocompose.todoapp.util.SearchAppBarState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun ListContent(
    allTasks: RequestState<List<ToDoTask>>,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    searchedTasks: RequestState<List<ToDoTask>>,
    searchedAppBarState: SearchAppBarState,
    lowPriorityTasks: List<ToDoTask>,
    highPriorityTasks: List<ToDoTask>,
    sortState: RequestState<Priority>,
    onSwipeToDismiss: (Action, ToDoTask) -> Unit

) {

    if (sortState is RequestState.Success) {
        when {
            searchedAppBarState == SearchAppBarState.TRIGGERED -> {
                if (searchedTasks is RequestState.Success) {
                    HandleListContent(
                        tasks = searchedTasks.data,
                        navigateToTaskScreen = navigateToTaskScreen,
                        onSwipeToDismiss = onSwipeToDismiss
                    )
                }
            }
            sortState.data == Priority.NONE -> {
                if (allTasks is RequestState.Success) {
                    HandleListContent(
                        tasks = allTasks.data,
                        navigateToTaskScreen = navigateToTaskScreen,
                        onSwipeToDismiss = onSwipeToDismiss
                    )
                }
            }
            sortState.data == Priority.LOW -> {
                HandleListContent(
                    tasks = lowPriorityTasks,
                    navigateToTaskScreen = navigateToTaskScreen, onSwipeToDismiss = onSwipeToDismiss
                )
            }
            sortState.data == Priority.HIGH -> {
                HandleListContent(
                    tasks = highPriorityTasks,
                    navigateToTaskScreen = navigateToTaskScreen, onSwipeToDismiss = onSwipeToDismiss
                )
            }
        }
    }


}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun HandleListContent(
    tasks: List<ToDoTask>,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    onSwipeToDismiss: (Action, ToDoTask) -> Unit
) {

    if (tasks.isEmpty()) {
        EmptyContent()
    } else {
        DisplayTasks(
            tasks = tasks,
            navigateToTaskScreen = navigateToTaskScreen,
            onSwipeToDismiss = onSwipeToDismiss
        )
    }
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun DisplayTasks(
    tasks: List<ToDoTask>,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    onSwipeToDismiss: (Action, ToDoTask) -> Unit
) {
    LazyColumn() {

        items(items = tasks, key = { it.id }) {
            val dismissState = rememberDismissState()
            val dismissDirection = dismissState.dismissDirection
            val degrees by animateFloatAsState(if (dismissState.targetValue == DismissValue.Default) 0f else -75f)
            val isDismissed = dismissState.isDismissed(DismissDirection.EndToStart)
            var itemVisibility by remember {
                mutableStateOf(false)
            }
            LaunchedEffect(key1 = true, block = {itemVisibility = true})
            if (isDismissed && dismissDirection == DismissDirection.EndToStart) {
                val scope = rememberCoroutineScope()
                scope.launch {
                    delay(300)
                    onSwipeToDismiss(Action.DELETE, it)

                }
            }
            AnimatedVisibility(
                visible = itemVisibility && !isDismissed,
                enter = expandVertically(animationSpec = tween(durationMillis = 300)),
                exit = shrinkVertically(animationSpec = tween(300))
            ) {

                SwipeToDismiss(
                    state = dismissState,
                    background = { RedBackground(degrees = degrees) },
                    directions = setOf(DismissDirection.EndToStart),
                    dismissThresholds = { FractionalThreshold(.2f) }, dismissContent = {
                        TaskItem(
                            toDoTask = it,
                            navigateToTaskScreen = navigateToTaskScreen
                        )
                    }
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun TaskItem(
    toDoTask: ToDoTask,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.ItemBackgroundColor,
        shape = RectangleShape,
        elevation = 2.dp,
        onClick = { navigateToTaskScreen(toDoTask.id) }
    ) {
        Column(
            modifier = Modifier
                .padding(LARGE_PADDING)
                .fillMaxWidth(),
        ) {

            Row() {

                Text(
                    text = toDoTask.title,
                    color = MaterialTheme.colors.TaskItemTextColor,
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    modifier = Modifier.weight(8f)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.TopEnd,
                ) {

                    Canvas(modifier = Modifier.size(PRIORITY_INDICATOR_SIZE)) {
                        drawCircle(color = toDoTask.priority.color)
                    }

                }


            }

            Text(
                text = toDoTask.description,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colors.TaskItemTextColor,
                style = MaterialTheme.typography.subtitle1,
                maxLines = 2, overflow = TextOverflow.Ellipsis
            )

        }

    }
}

@Composable
fun RedBackground(degrees: Float) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(HighPriorityColor)
            .padding(horizontal = 24.dp), contentAlignment = Alignment.CenterEnd
    ) {

        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete",
            tint = Color.White,
            modifier = Modifier.rotate(degrees)
        )

    }

}

@ExperimentalMaterialApi
@Preview
@Composable
fun TaskItemPreview() {

    TaskItem(
        toDoTask = ToDoTask(0, title = "title", description = "Description ", Priority.HIGH),
        navigateToTaskScreen = {}
    )


}