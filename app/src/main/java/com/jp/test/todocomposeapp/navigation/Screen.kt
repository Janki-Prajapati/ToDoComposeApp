package com.jp.test.todocomposeapp.navigation


const val TASK_ARGUMENT_KEY = "calledFrom"
const val TASK_ARGUMENT_VALUE_1 = "Add"
const val TASK_ARGUMENT_VALUE_2 = "Edit"


sealed class Screen(val route : String) {
    data object Splash : Screen(route = "splash_screen")
    data object Home : Screen(route = "home_screen")
    data object AddTask : Screen(route = "add_task_screen/{$TASK_ARGUMENT_KEY}"){
        fun passCalledFrom(calledFrom : String) : String{
            return this.route.replace(oldValue = "{$TASK_ARGUMENT_KEY}", newValue = calledFrom)
        }
    }
}