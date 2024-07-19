package com.jp.test.todocomposeapp.commonviews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.jp.test.todocomposeapp.R
import com.jp.test.todocomposeapp.ui.theme.ColorYellowTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarWithSearch(
    searchQuery: TextFieldValue,
    onSearchQueryChange: (TextFieldValue) -> Unit,
    onCloseSearchClicked: () -> Unit,
    onClearSearchClicked: () -> Unit
) {
    TopAppBar(
        title = {
            BasicTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(12.dp),
                textStyle = TextStyle.Default.copy(
                    fontFamily = FontFamily.Serif,
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    fontWeight = FontWeight.Normal
                )
            )
        },
        navigationIcon = {
            IconButton(onClick = onCloseSearchClicked) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.txt_close_search)
                )
            }
        },
        actions = {
            IconButton(onClick = onClearSearchClicked) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.txt_clear_search)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = ColorYellowTheme,
            titleContentColor = Color.Black
        )
    )
}