package com.jp.test.todocomposeapp.commonviews

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.jp.test.todocomposeapp.R
import com.jp.test.todocomposeapp.models.Priority
import com.jp.test.todocomposeapp.ui.theme.ColorYellowTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTopBar(
    filterMenuDisplayed: Boolean,
    moreMenuDisplayed: Boolean,
    list: List<Priority>,
    selectedFilterId: (Int) -> Unit,
    deleteAllClicked: () -> Unit,
    filterClicked: () -> Unit,
    moreClicked: () -> Unit,
    onSearchClicked: () -> Unit
) {
    TopAppBar(
        title = { Text(text = stringResource(R.string.home_screen_title_tasks)) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = ColorYellowTheme,
            titleContentColor = Color.Black
        ),
        actions = {
            IconButton(onClick = onSearchClicked) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.text_search),
                    tint = Color.Black
                )
            }
            IconButton(onClick = filterClicked) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_sort),
                    contentDescription = "sort", tint = Color.Black
                )
            }

            DropdownMenu(
                offset = DpOffset((-45).dp, 0.dp),
                expanded = filterMenuDisplayed,
                onDismissRequest = filterClicked
            ) {

                list.forEach {
                    DropdownMenuItem(
                        text = {
                            PriorityItem(
                                priorityText = it.name,
                                priorityColor = it.color,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(3.dp)
                            )
                        },
                        contentPadding = PaddingValues(5.dp),
                        onClick = {
                            filterClicked()
                            selectedFilterId(it.id)
                        })
                }


            }

            IconButton(onClick = moreClicked) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = stringResource(R.string.menuicon),
                    tint = Color.Black
                )
            }


            DropdownMenu(
                expanded = moreMenuDisplayed,
                onDismissRequest = moreClicked
            ) {
                DropdownMenuItem(
                    text = { Text(text = stringResource(R.string.delete_all_tasks)) },
                    contentPadding = PaddingValues(5.dp),
                    onClick = {
                        moreClicked()
                        deleteAllClicked()
                    })
            }

        }
    )
}