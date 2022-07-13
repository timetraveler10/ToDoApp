package com.todocompose.todoapp.componets

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.todocompose.todoapp.data.models.Priority
import com.todocompose.todoapp.ui.theme.PRIORITY_DROP_DOWN_HEIGHT
import com.todocompose.todoapp.ui.theme.PRIORITY_INDICATOR_SIZE

@Composable
fun PriorityDropDown(priority: Priority, onPrioritySelected: (Priority) -> Unit) {

    var expanded by remember { mutableStateOf(false) }
    val angle: Float by animateFloatAsState(targetValue = if (expanded) 180f else 0f)

//    var isFocused by remember { mutableStateOf(false) }
//    val color = animateColorAsState(
//        targetValue = if (isFocused) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface.copy(
//            alpha = ContentAlpha.disabled
//        )
//    )

    Row(modifier = Modifier
        .fillMaxWidth()
        .height(PRIORITY_DROP_DOWN_HEIGHT)
        .clickable {
            expanded = !expanded
        }
        .border(
            1.dp,
            color = MaterialTheme.colors.onSurface.copy(
                alpha = ContentAlpha.disabled
            ),
            shape = RoundedCornerShape(5.dp)
        )
        .focusable(enabled = true), verticalAlignment = Alignment.CenterVertically
    ) {

        Canvas(
            modifier = Modifier
                .size(PRIORITY_INDICATOR_SIZE)
                .weight(1f)
        ) {
            drawCircle(color = priority.color)
        }
        Text(
            text = priority.name,
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.weight(8f)
        )
        IconButton(
            onClick = { expanded = true },
            modifier = Modifier
                .alpha(ContentAlpha.medium)
                .rotate(angle)
                .weight(1.5f)
        ) {
            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "DropDown")

        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth(fraction = 0.94f)
        ) {

            DropdownMenuItem(onClick = {
                expanded = false
                onPrioritySelected(Priority.LOW)
            })
            { PriorityItem(priority = Priority.LOW) }

            DropdownMenuItem(onClick = {
                expanded = false
                onPrioritySelected(Priority.MEDIUM)
            })
            { PriorityItem(priority = Priority.MEDIUM) }

            DropdownMenuItem(onClick = {
                expanded = false
                onPrioritySelected(Priority.HIGH)
            })
            { PriorityItem(priority = Priority.HIGH) }

        }

    }
}

@Preview
@Composable
fun DropDownPreview() {

    PriorityDropDown(priority = Priority.MEDIUM, onPrioritySelected = {})

}