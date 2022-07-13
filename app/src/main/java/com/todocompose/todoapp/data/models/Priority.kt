package com.todocompose.todoapp.data.models

import androidx.compose.ui.graphics.Color
import com.todocompose.todoapp.ui.theme.HighPriorityColor
import com.todocompose.todoapp.ui.theme.LowPriorityColor
import com.todocompose.todoapp.ui.theme.MediumPriorityColor
import com.todocompose.todoapp.ui.theme.NonePriorityColor
import com.todocompose.todoapp.util.Action

enum class Priority(val color: Color) {
    HIGH(HighPriorityColor),
    MEDIUM(MediumPriorityColor),
    LOW(LowPriorityColor),
    NONE(NonePriorityColor)
}