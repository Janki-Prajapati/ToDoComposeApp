package com.jp.test.todocomposeapp.commonviews

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jp.test.todocomposeapp.R
import com.jp.test.todocomposeapp.models.Priority
import com.jp.test.todocomposeapp.ui.theme.ColorGreen
import com.jp.test.todocomposeapp.ui.theme.ColorRed
import com.jp.test.todocomposeapp.ui.theme.ColorYellow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DynamicSelectTextField(
    modifier: Modifier = Modifier,
    selectedValue: Priority? = null,
    itemList: List<Priority>,
    onValueChangedEvent: (Priority) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val icon = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown


    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
                .border(width = 1.dp, Color.LightGray)
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(all = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {

                PriorityItem(
                    priorityText = selectedValue?.name ?: "",
                    priorityColor = itemList.find { it == selectedValue }?.color
                        ?: Color.Transparent,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                )

                Icon(
                    imageVector = icon,
                    contentDescription = stringResource(R.string.text_dropdownicon)
                )


            }
        }

        ExposedDropdownMenu(
            modifier = Modifier
                .background(Color.White)
                .padding(all = 5.dp),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            itemList.onEachIndexed { index, item ->
                Box(
                    modifier = Modifier
                        .clickable {
                            onValueChangedEvent(item)
                            expanded = !expanded
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Column {
                        if (index != 0) {
                            HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
                        }

                        Row(
                            modifier = Modifier
                                .padding(all = 3.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier.weight(1f),
                                text = item.name,
                                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                fontWeight = FontWeight.Normal,
                                fontStyle = FontStyle.Normal,
                            )

                            Box(
                                modifier = Modifier
                                    .size(12.dp)
                                    .clip(CircleShape)
                                    .background(item.color)
                            )
                        }
                    }

                }
            }
        }
    }
}

@Preview
@Composable
private fun DynamicSelectTextFieldPreview() {
    DynamicSelectTextField(selectedValue = Priority(1, "High", ColorRed, isSelected = true),
        itemList = listOf(
            Priority(1, "High", ColorRed, isSelected = false),
            Priority(2, "Medium", ColorYellow, isSelected = false),
            Priority(3, "Low", ColorGreen, isSelected = true)
        ),
        onValueChangedEvent = {})
}



