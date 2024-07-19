package com.jp.test.todocomposeapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jp.test.todocomposeapp.R
import com.jp.test.todocomposeapp.TaskViewModel
import com.jp.test.todocomposeapp.commonviews.CustomTopBar
import com.jp.test.todocomposeapp.commonviews.TaskListItem
import com.jp.test.todocomposeapp.navigation.Screen
import com.jp.test.todocomposeapp.navigation.TASK_ARGUMENT_VALUE_1
import com.jp.test.todocomposeapp.navigation.TASK_ARGUMENT_VALUE_2
import com.jp.test.todocomposeapp.ui.theme.ColorYellowBg

@Composable
fun HomeScreen(navController: NavHostController, taskViewModel: TaskViewModel = hiltViewModel()) {

    var isMenuDisplay by remember { mutableStateOf(false) }
    var isFilterMenuDisplay by remember { mutableStateOf(false) }
    var isSearchActive by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    val taskList by taskViewModel.dataList.collectAsStateWithLifecycle()

    val systemUiController = rememberSystemUiController()
    LaunchedEffect(key1 = Unit) {
        systemUiController.setSystemBarsColor(
            color = ColorYellowBg
        )
        taskViewModel.getAllTasks()
    }

    Scaffold(
        topBar = {
            CustomTopBar(
                isSearchActive = isSearchActive,
                searchQuery = searchQuery,
                filterMenuDisplayed = isFilterMenuDisplay,
                moreMenuDisplayed = isMenuDisplay,
                list = taskViewModel.priorityList,
                onSearchQueryChange = {
                    searchQuery = it
                    taskViewModel.getTasksWithSearchQuery(searchQuery.text)
                },
                onSearchClicked = {
                    isSearchActive = true
                    taskViewModel.getTasksWithSearchQuery(searchQuery.text)
                },
                onCloseSearchClicked = {
                    isSearchActive = false
                    searchQuery = TextFieldValue("")
                    taskViewModel.getTasksWithSearchQuery(searchQuery.text)
                },
                onClearSearchClicked = {
                    searchQuery = TextFieldValue("")
                    taskViewModel.getTasksWithSearchQuery(searchQuery.text)
                },
                selectedFilterId = { taskViewModel.getTasksWithId(it) },
                deleteAllClicked = { taskViewModel.deleteAllTasks() },
                filterClicked = {
                    isFilterMenuDisplay = !isFilterMenuDisplay
                },
                moreClicked = {
                    isMenuDisplay = !isMenuDisplay
                })
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = ColorYellowBg,
                shape = CircleShape,
                onClick = {
                    navController.navigate(
                        route = Screen.AddTask.passCalledFrom(
                            TASK_ARGUMENT_VALUE_1
                        )
                    )
                }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.text_add)
                )
            }
        }

    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
        ) {

            if (taskList.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_sad),
                        contentDescription = stringResource(R.string.text_no_tasks_found),
                        alpha = 0.5f
                    )
                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = "No Task",
                        color = Color.Gray,
                        fontSize = MaterialTheme.typography.labelLarge.fontSize,
                        fontWeight = FontWeight.SemiBold,
                        fontStyle = FontStyle.Normal,
                        fontFamily = FontFamily.Monospace
                    )
                }
            } else {
                taskList.forEach { task ->
                    val indicatorColor =
                        taskViewModel.priorityList.find { it.id == task.priority }?.color
                            ?: Color.Transparent
                    TaskListItem(
                        titleText = task.title,
                        descriptionText = task.description,
                        indicatorColor = indicatorColor
                    ) {
                        navController.navigate(
                            route = Screen.AddTask.passCalledFrom(
                                TASK_ARGUMENT_VALUE_2
                            )
                        )
                    }
                }
            }


        }
    }
}


