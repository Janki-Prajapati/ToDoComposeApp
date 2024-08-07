package com.jp.test.todocomposeapp.screens

import android.content.Context
import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import com.jp.test.todocomposeapp.activity.MainActivity
import com.jp.test.todocomposeapp.navigation.Screen
import com.jp.test.todocomposeapp.navigation.SetupNavController
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SplashScreenKtTest {

    private lateinit var navController: TestNavHostController

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        navController = TestNavHostController(ApplicationProvider.getApplicationContext<Context>())
        composeTestRule.activity.setContent {
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            SetupNavController(navController = navController)
        }
    }

    @Test
    fun verify_splash_screen_is_displayed() {
        // Check the start destination
        composeTestRule.onNodeWithTag("splash_screen").assertIsDisplayed()
    }

    @Test
    fun verify_home_screen_after_splash_screen() {
        // Check the splash screen is displayed
        composeTestRule.onNodeWithTag("splash_screen").assertIsDisplayed()

        composeTestRule.waitUntil(timeoutMillis = 5000) {
            navController.currentDestination?.route == Screen.Home.route
        }

        composeTestRule.onNodeWithTag("Home_screen").assertIsDisplayed()
    }
}