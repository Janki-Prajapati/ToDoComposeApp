package com.jp.test.todocomposeapp.screens

import android.content.res.Configuration
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jp.test.todocomposeapp.R
import com.jp.test.todocomposeapp.navigation.Screen
import com.jp.test.todocomposeapp.ui.theme.ColorGreenTheme
import kotlinx.coroutines.delay

@Composable
fun AnimatedSplashScreen(navController: NavHostController) {
    var startAnimation by remember {
        mutableStateOf(false)
    }

    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 0f else 1000f,
        animationSpec = tween(durationMillis = 3000), label = ""
    )

    val systemUiController = rememberSystemUiController()
    LaunchedEffect(key1 = true) {
        systemUiController.setSystemBarsColor(
            color = ColorGreenTheme
        )
        startAnimation = true
        delay(4000)
        navController.popBackStack()
        navController.navigate(Screen.Home.route)

    }
    Splash(alpha = alphaAnim.value)
}


@Composable
fun Splash(alpha: Float) {
    Box(
        modifier = Modifier
            .testTag("splash_screen")
            .background(if (isSystemInDarkTheme()) Color.Black else ColorGreenTheme)
            .fillMaxSize(),
        contentAlignment = Alignment.Center

    ) {
        Icon(
            modifier = Modifier
                .offset { IntOffset(0, alpha.toInt()) }
                .size(120.dp),
            painter = painterResource(id = R.drawable.ic_splash),
            contentDescription = stringResource(R.string.text_logo_icon),
            tint = Color.White
        )
    }
}

@Preview
@Composable
private fun SplashPreview() {
    Splash(alpha = 1f)
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DarkSplashPreview() {
    Splash(alpha = 1f)
}