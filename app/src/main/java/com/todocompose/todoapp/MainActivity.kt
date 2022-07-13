package com.todocompose.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.userui.SystemUiController
import com.todocompose.todoapp.ui.theme.ToDoAppTheme
import com.todocompose.todoapp.ui.theme.navigation.SetupNavigation
import com.todocompose.todoapp.ui.theme.view_models.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private val sharedViewModel: SharedViewModel by viewModels()
    @ExperimentalAnimationApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {

            ToDoAppTheme {
                SystemUiController()

                navController = rememberNavController()
                SetupNavigation(navController = navController, sharedViewModel = sharedViewModel)

            }
        }
    }
}

