package com.jp.test.todocomposeapp.commonviews

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jp.test.todocomposeapp.ui.theme.ColorGreenBg

@Composable
fun TaskListItem(
    titleText: String,
    descriptionText: String,
    indicatorColor: Color,
    index: Int,
    onTaskItemClick: (Int) -> Unit
) {
    Column(modifier = Modifier
        .background(Color.White)
        .padding(horizontal = 16.dp, vertical = 8.dp)
        .clickable(indication = null, interactionSource = remember {
            MutableInteractionSource()
        }) { onTaskItemClick(index) }) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                modifier = Modifier.weight(1f),
                text = titleText,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Normal,
                fontFamily = FontFamily.Serif
            )

            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(indicatorColor)
            )
        }

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = descriptionText,
            fontSize = MaterialTheme.typography.titleSmall.fontSize,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
            fontFamily = FontFamily.Serif
        )


    }
}

@Preview(showSystemUi = true)
@Composable
private fun TaskListItemPreview() {
//    TaskListItem("Title", "Description", Color.Red) {}
}