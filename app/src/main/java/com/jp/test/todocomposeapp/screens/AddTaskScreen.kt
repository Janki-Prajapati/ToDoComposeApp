package com.jp.test.todocomposeapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jp.test.todocomposeapp.R
import com.jp.test.todocomposeapp.TaskViewModel
import com.jp.test.todocomposeapp.commonviews.DynamicSelectTextField
import com.jp.test.todocomposeapp.database.Task
import com.jp.test.todocomposeapp.ui.theme.ColorYellowBg
import com.jp.test.todocomposeapp.ui.theme.ColorYellowTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(navController: NavHostController, calledFrom: String) {
    val taskViewModel = hiltViewModel<TaskViewModel>()

    val context = LocalContext.current

    val systemUiController = rememberSystemUiController()
    LaunchedEffect(key1 = Unit) {
        systemUiController.setSystemBarsColor(
            color = ColorYellowBg
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.add_task)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = ColorYellowTheme,
                    titleContentColor = Color.Black
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = stringResource(
                                R.string.back_icon
                            )
                        )
                    }
                },
                actions = {
                    if (calledFrom == "Edit") {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = stringResource(R.string.delete_icon),
                                tint = Color.Black
                            )
                        }
                    }
                    IconButton(onClick = {
                        if (taskViewModel.validateTitle() && taskViewModel.validateDescription()) {
                            taskViewModel.insertDataToDb(
                                Task(
                                    title = taskViewModel.formState.title,
                                    priority = taskViewModel.formState.priorityId,
                                    description = taskViewModel.formState.description
                                )
                            )
                            navController.popBackStack()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = stringResource(R.string.save_icon),
                            tint = Color.Black
                        )
                    }
                }
            )
        }

    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
        ) {

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp),
                value = taskViewModel.formState.title,
                onValueChange = {
                    taskViewModel.onEvent(TaskViewModel.TaskEvent.TitleChanged(it))
                },
                label = { Text(text = stringResource(R.string.text_title)) },
                placeholder = { Text(text = stringResource(R.string.text_title)) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    capitalization = KeyboardCapitalization.Words,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                singleLine = true,
                isError = taskViewModel.formState.titleError != null,
                textStyle = TextStyle.Default.copy(
                    fontFamily = FontFamily.Serif,
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    fontWeight = FontWeight.Normal
                ),
                shape = RoundedCornerShape(1.dp)
            )

            if (taskViewModel.formState.titleError != null) {
                Text(
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 4.dp),
                    text = taskViewModel.formState.titleError!!.asString(context),
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }

            DynamicSelectTextField(
                selectedValue = taskViewModel.priorityList.find { it.isSelected },
                itemList = taskViewModel.priorityList,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp),
                onValueChangedEvent = { newValue ->
                    taskViewModel.updatePriority(newValue.name)
                    taskViewModel.onEvent(TaskViewModel.TaskEvent.PriorityChanged(newValue.id))
                })

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(all = 10.dp),
                value = taskViewModel.formState.description,
                onValueChange = {
                    taskViewModel.onEvent(TaskViewModel.TaskEvent.DescriptionChanged(it))
                },
                label = { Text(text = stringResource(R.string.text_description)) },
                placeholder = { Text(text = stringResource(R.string.text_description)) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    capitalization = KeyboardCapitalization.Words,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                singleLine = true,
                isError = taskViewModel.formState.descriptionError != null,
                textStyle = TextStyle.Default.copy(
                    fontFamily = FontFamily.Serif,
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    fontWeight = FontWeight.Normal
                ),
                shape = RoundedCornerShape(1.dp)
            )

            if (taskViewModel.formState.descriptionError != null) {
                Text(
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 4.dp),
                    text = taskViewModel.formState.descriptionError!!.asString(context),
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }


    }
}


@Preview
@Composable
private fun AddTaskScreenPreview() {
    AddTaskScreen(rememberNavController(), "Add")
}