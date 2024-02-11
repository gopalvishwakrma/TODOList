package com.example.todoapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.ui.theme.UserInput
import com.example.todoapp.ui.theme.UserInput.Companion.UserInputPopUp

/**
 * This file contains the code for a TodoList composable function. It displays a list of tasks
 * retrieved from a DAO and allows the user to add, delete, and mark tasks as done. The UI is built
 * using Jetpack Compose components such as Scaffold, TopAppBar, BottomAppBar, FloatingActionButton,
 * Text, Checkbox, Icon, Divider, etc.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoList(
    todoDao: TODO_Dao,
    onTaskClick: (TODO_Entity) -> Unit,
    onTaskDelete: (TODO_Entity) -> Unit
) {
    // State to control the visibility of the task input popup
    var isPopupVisible by remember { mutableStateOf(false) }

    // Retrieve tasks from the DAO and collect them as a state
    val tasks by todoDao.getAllTasks().collectAsState(initial = emptyList())

    // Build the UI using Jetpack Compose components
    Scaffold(
        topBar = {
            // Top app bar
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Row {
                        // Todo logo icon
                        Icon(
                            painter = painterResource(id = R.drawable.todologoicon),
                            contentDescription = "Todo logo",
                            modifier = Modifier
                                .size(28.dp)
                                .align(alignment = Alignment.CenterVertically)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Your Todo Lists")
                    }
                }
            )
        },
        bottomBar = {
            // Bottom app bar
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .height(60.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "\"Opportunities don't happen, you create them\"",
                    style = TextStyle(
                        fontStyle = FontStyle.Italic,
                        fontSize = 15.sp,
                    )
                )
            }
        },
        floatingActionButton = {
            // Floating action button for adding tasks
            FloatingActionButton(onClick = { isPopupVisible = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            if (tasks.isEmpty()) {
                // Display message when there are no tasks
                Text(
                    text = "No tasks",
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF3A3B3C),
                    fontSize = 52.sp,
                    fontStyle = FontStyle.Italic
                )
            }
            Column(
                modifier = Modifier.padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                LazyColumn {
                    itemsIndexed(tasks) { index, task ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            // Task number
                            Text(
                                text = "${index + 1}.",
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            // Task description
                            Text(
                                text = task.task,
                                modifier = Modifier.weight(1f)
                            )
                            // Checkbox for marking task as done
                            Checkbox(
                                checked = task.isDone,
                                onCheckedChange = { isChecked ->
                                    val updatedTask = task.copy(isDone = isChecked)
                                    onTaskClick(updatedTask)
                                },
                                modifier = Modifier.size(5.dp)
                            )

                            Spacer(modifier = Modifier.width(18.dp))
                            // Delete task icon
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_delete_outline_24),
                                contentDescription = "Delete",
                                modifier = Modifier
                                    .size(26.dp)
                                    .clickable { onTaskDelete(task) }
                            )
                        }
                        // Divider between tasks
                        Divider(
                            color = Color.Gray,
                            thickness = 1.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        )
                    }
                }
                if (isPopupVisible) {
                    // Task input popup
                    UserInput.UserInputPopUp(
                        onDismiss = {
                            isPopupVisible = false
                        },
                        onAdd = { newTask ->
                            onTaskClick(TODO_Entity(task = newTask))
                            isPopupVisible = false
                        }
                    )
                }
            }
        }
    }
}