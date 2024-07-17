package com.jp.test.todocomposeapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jp.test.todocomposeapp.R
import com.jp.test.todocomposeapp.TaskViewModel
import com.jp.test.todocomposeapp.commonviews.TaskListItem
import com.jp.test.todocomposeapp.navigation.Screen
import com.jp.test.todocomposeapp.ui.theme.ColorYellowBg
import com.jp.test.todocomposeapp.ui.theme.ColorYellowTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, taskViewModel: TaskViewModel = hiltViewModel()) {
    val context = LocalContext.current
    var isMenuDisplay by remember { mutableStateOf(false) }
    var isFilterMenuDisplay by remember { mutableStateOf(false) }
    var isFilterDataDisplay by remember { mutableStateOf(false) }

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
            TopAppBar(
                title = { Text(text = "Tasks") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = ColorYellowTheme,
                    titleContentColor = Color.Black
                ),
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "search",
                            tint = Color.Black
                        )
                    }
                    IconButton(onClick = {isFilterMenuDisplay = !isFilterMenuDisplay }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_sort),
                            contentDescription = "sort", tint = Color.Black
                        )
                    }

                    DropdownMenu(
                        expanded = isFilterMenuDisplay,
                        onDismissRequest = { isFilterMenuDisplay = !isFilterMenuDisplay }) {

                        taskViewModel.priorityList.forEach {
                            DropdownMenuItem(
                                text = { Text(text = it.name) },
                                contentPadding = PaddingValues(5.dp),
                                onClick = {
                                    isFilterMenuDisplay = !isFilterMenuDisplay
                                    taskViewModel.getTasksWithId(it.id)
                                    println("==>> ${taskList.size} for id ${it.id}")
                                })
                        }

                    }

                    IconButton(onClick = { isMenuDisplay = !isMenuDisplay }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "menuIcon",
                            tint = Color.Black
                        )
                    }

                    MenuDropDown(
                        isMenuDisplay = isMenuDisplay,
                        closeDropdown = { isMenuClose -> isMenuDisplay = isMenuClose },
                        deleteAllClicked = {
                            isMenuDisplay = !isMenuDisplay
                            taskViewModel.deleteAllTasks()
                        })

                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = ColorYellowBg,
                shape = CircleShape,
                onClick = { navController.navigate(Screen.AddTask.route) }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
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
                        contentDescription = "No Tasks",
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
                    )
                }
            }


        }
    }
}

@Composable
fun MenuDropDown(
    isMenuDisplay: Boolean,
    closeDropdown: (Boolean) -> Unit,
    deleteAllClicked: () -> Unit
) {
    DropdownMenu(expanded = isMenuDisplay, onDismissRequest = { closeDropdown(!isMenuDisplay) }) {
        DropdownMenuItem(
            text = { Text(text = stringResource(R.string.delete_all_tasks)) },
            contentPadding = PaddingValues(5.dp),
            onClick = { deleteAllClicked() })
    }
}


