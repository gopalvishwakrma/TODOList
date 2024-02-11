package com.example.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.todoapp.ui.theme.TodoAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Called when the activity is first created.
 * @param savedInstanceState The saved state of the activity
 */

class MainActivity : ComponentActivity() {
    private lateinit var todoDao: TODO_Dao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get the database instance
        val database = TODO_Database.getDatabase(this)
        todoDao = database.getTODO_Dao()

        // Set up the UI using Jetpack Compose
        setContent {
            TodoAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Display the list of tasks
                    TodoList(
                        todoDao = todoDao,
                        onTaskClick = { updatedTask ->
                            // Update the task in the database when it's clicked
                            CoroutineScope(Dispatchers.IO).launch {
                                todoDao.insertTask(updatedTask)
                            }
                        },

                        onTaskDelete = { task ->
                            // Delete the task from the database when the delete icon is clicked
                            CoroutineScope(Dispatchers.IO).launch {
                                todoDao.deleteTask(task)
                            }
                        }
                    )
                }
            }
        }
    }
}



