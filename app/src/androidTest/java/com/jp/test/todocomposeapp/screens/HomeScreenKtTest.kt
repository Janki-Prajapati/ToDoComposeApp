package com.jp.test.todocomposeapp.screens

import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jp.test.todocomposeapp.activity.MainActivity
import com.jp.test.todocomposeapp.commonviews.CustomTopBar
import com.jp.test.todocomposeapp.commonviews.DefaultTopBar
import com.jp.test.todocomposeapp.commonviews.TaskListItem
import com.jp.test.todocomposeapp.database.Task
import com.jp.test.todocomposeapp.navigation.Screen
import com.jp.test.todocomposeapp.navigation.SetupNavController
import com.jp.test.todocomposeapp.ui.theme.ToDoComposeAppTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenKtTest {

    /*    @get:Rule(order = 1)
        val hiltRule = HiltAndroidRule(this)*/

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
//        hiltRule.inject()
        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        composeTestRule.activity.setContent {
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            SetupNavController(navController = navController)
        }

    }

    @Test
    fun verify_home_screen_is_displayed() {
        // Check the splash screen is displayed
        composeTestRule.onNodeWithTag("splash_screen").assertIsDisplayed()

        composeTestRule.waitUntil(timeoutMillis = 5000) {
            navController.currentDestination?.route == Screen.Home.route
        }

        composeTestRule.onNodeWithTag("Home_screen").assertIsDisplayed()
    }

    @Test
    fun test_data_displayed() {
        // Check that each item in the list is displayed
        val task1 = Task(1, "Task1", 1, "Description1")
        val task2 = Task(2, "Task2", 2, "Description2")
        val task3 = Task(3, "Task3", 3, "Description3")
        val items = listOf(task1, task2, task3)

        composeTestRule.activity.setContent {
            ToDoComposeAppTheme {
                items.forEachIndexed { index, task ->
                    TaskListItem(
                        task,
                        indicatorColor = Color.LightGray,
                        onTaskItemClick = {
                        },
                        onRemove = {
                        }
                    )
                }
            }
        }
        // Get all nodes with the specified tag
        val listItems =
            composeTestRule.onAllNodesWithTag("task_item_test_tag").fetchSemanticsNodes()

        // Check that the correct number of items are displayed
        assert(listItems.size == 3)
    }

    @Test
    fun test_home_screen_toolbar_displayed(){
        composeTestRule.activity.setContent {
            DefaultTopBar(
                filterMenuDisplayed = false,
                moreMenuDisplayed = false,
                list = emptyList(),
                onSearchClicked = {},
                selectedFilterId = {},
                deleteAllClicked = {  },
                filterClicked = {},
                moreClicked = {})
        }
// Ensure Compose is idle before performing assertions
        composeTestRule.waitForIdle()

        // Print the UI hierarchy to debug
        composeTestRule.onRoot().printToLog("TAG")

        composeTestRule.onNodeWithTag("home_top_bar_title").assertIsDisplayed()

    }
}