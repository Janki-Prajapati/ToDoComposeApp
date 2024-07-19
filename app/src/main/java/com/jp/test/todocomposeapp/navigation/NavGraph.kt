package com.jp.test.todocomposeapp.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jp.test.todocomposeapp.screens.AddTaskScreen
import com.jp.test.todocomposeapp.screens.AnimatedSplashScreen
import com.jp.test.todocomposeapp.screens.HomeScreen

@Composable
fun SetupNavController(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(route = Screen.Splash.route) {
            AnimatedSplashScreen(navController)
        }
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(route = Screen.AddTask.route, arguments = listOf(navArgument(TASK_ARGUMENT_KEY) {
            type = NavType.StringType
        })) {
            println("args ==>> ${it.arguments?.getString(TASK_ARGUMENT_KEY).toString()}")
            AddTaskScreen(navController = navController, calledFrom = it.arguments?.getString(TASK_ARGUMENT_KEY).toString())
        }
    }
}