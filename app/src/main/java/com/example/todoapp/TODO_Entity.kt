package com.example.todoapp

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * TODO_Entity.kt
 *
 * This file contains the definition of the TODO_Entity data class. It represents a single TODO item
 * with properties such as id, task, and isDone.
 *
 * The id is a unique identifier for each TODO item and is auto-generated.
 * The task represents the description of the TODO item.
 * The isDone property indicates whether the task is completed or not.
 */

@Entity(tableName = "todo_table")
data class TODO_Entity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val task: String = "",
    var isDone: Boolean = false
)

