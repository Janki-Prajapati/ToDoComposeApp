package com.jp.test.todocomposeapp.commonviews

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.TextFieldValue
import com.jp.test.todocomposeapp.models.Priority

@Composable
fun CustomTopBar(
    isSearchActive: Boolean,
    searchQuery: TextFieldValue,
    filterMenuDisplayed: Boolean,
    moreMenuDisplayed: Boolean,
    list: List<Priority>,
    onSearchQueryChange: (TextFieldValue) -> Unit,
    onSearchClicked: () -> Unit,
    onCloseSearchClicked: () -> Unit,
    onClearSearchClicked: () -> Unit,
    selectedFilterId: (Int) -> Unit,
    deleteAllClicked: () -> Unit,
    filterClicked: () -> Unit,
    moreClicked: () -> Unit
) {
    if (isSearchActive) {
        TopBarWithSearch(
            searchQuery = searchQuery,
            onSearchQueryChange = onSearchQueryChange,
            onCloseSearchClicked = onCloseSearchClicked,
            onClearSearchClicked = onClearSearchClicked
        )
    } else {
        DefaultTopBar(
            filterMenuDisplayed = filterMenuDisplayed,
            moreMenuDisplayed = moreMenuDisplayed,
            list = list,
            selectedFilterId = selectedFilterId,
            deleteAllClicked = deleteAllClicked,
            filterClicked = filterClicked,
            moreClicked = moreClicked,
            onSearchClicked = onSearchClicked
        )
    }
}