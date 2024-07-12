package com.jp.test.todocomposeapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 1,
    var title: String,
    var priority: Int,
    var description: String
)
