package com.jp.test.todocomposeapp.models

import androidx.compose.ui.graphics.Color


data class Priority(val id: Int, val name: String, val color: Color, var isSelected: Boolean)
