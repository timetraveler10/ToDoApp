package com.todocompose.todoapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val LightGrey = Color(0xFFFCFCFC)
val MediumGrey = Color(0xFF9C9C9C)
val DarkGrey = Color(0xFF141414)

val LowPriorityColor = Color(0xFF00C980)
val MediumPriorityColor = Color(0xFFFFC114)
val HighPriorityColor = Color(0xFFFF4646)
val NonePriorityColor = MediumGrey

val Colors.TopAppBarContentColor: Color
    @Composable get() = if (isSystemInDarkTheme()) LightGrey else Color.White

val Colors.SplashScreenBackground: Color
    @Composable get() = if (isSystemInDarkTheme()) Color.Black else Purple700

val Colors.TopAppBarBackground: Color
    @Composable get() = if (isSystemInDarkTheme()) Color.Black else Purple500
val Colors.FabBackgroundColor: Color
    @Composable get() = if (isSystemInDarkTheme()) Purple700 else Teal200
val Colors.ItemBackgroundColor: Color
    @Composable get() = if (isSystemInDarkTheme()) DarkGrey else MaterialTheme.colors.surface

val Colors.TaskItemTextColor: Color
    @Composable get() = if (isSystemInDarkTheme()) LightGrey else DarkGrey
