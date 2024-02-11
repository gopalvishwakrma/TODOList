package com.example.todoapp.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

/**
 * Composable function to display a dialog for user input
 * @param onDismiss callback function to handle dismissal of dialog
 * @param onAdd callback function to handle addition of user input
 */

class UserInput {
    companion object {
        @Composable
        fun UserInputPopUp(onDismiss: () -> Unit, onAdd: (String) -> Unit) {
            // Local state to hold the text input
            var text by remember { mutableStateOf(TextFieldValue()) }

            // Display a dialog with rounded corners
            Dialog(
                onDismissRequest = { onDismiss() }, // Dismiss when clicking outside the dialog
                properties = DialogProperties(
                    dismissOnClickOutside = true,
                ),
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(10.dp), // Apply border-radius
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Column to arrange UI elements vertically
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Text input field with label "Task"
                        OutlinedTextField(
                            value = text,
                            onValueChange = { newText -> text = newText },
                            label = { Text("Task") },
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Done
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        // Spacer to add some vertical space
                        Spacer(modifier = Modifier.height(16.dp))

                        // Row to arrange buttons horizontally
                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            // Cancel button
                            Button(
                                onClick = onDismiss,
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(40.dp)
                            ) {
                                Text(
                                    "Cancel",
                                    fontSize = 14.sp
                                )
                            }

                            // Spacer to add space between buttons
                            Spacer(modifier = Modifier.width(8.dp))

                            // Add button
                            Button(
                                onClick = {
                                    onAdd(text.text)
                                    onDismiss()
                                },
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(40.dp)
                            ) {
                                Text(
                                    text = "Add",
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}