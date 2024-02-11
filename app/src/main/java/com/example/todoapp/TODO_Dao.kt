package com.example.todoapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

/**
 * This file contains the DAO (Data Access Object) interface for the TODO_Entity.
 * It provides methods for inserting and deleting tasks, as well as retrieving all tasks from the database.
 */

@Dao
interface TODO_Dao {
    // Inserts a task into the database
    @Upsert
    suspend fun insertTask(todo_entity: TODO_Entity)

    // Deletes a task from the database
    @Delete
    suspend fun deleteTask(todo_entity: TODO_Entity)

    // Retrieves all tasks from the database
    @Query("SELECT * FROM todo_table")
    fun getAllTasks(): Flow<List<TODO_Entity>>
}
