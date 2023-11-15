package com.guilherme.tasks.tasks

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.guilherme.tasks.core.TaskEvent
import com.guilherme.tasks.core.TaskState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(
    state: TaskState,
    visible: Boolean,
    modifier: Modifier = Modifier,
    onEvent: (TaskEvent) -> Unit
) {
    AnimatedVisibility(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background),
        visible = visible,
        enter = slideInVertically(
            animationSpec = tween(durationMillis = 300),
            initialOffsetY = { it }
        ),
        exit = slideOutVertically(
            animationSpec = tween(durationMillis = 300),
            targetOffsetY = { it }
        )
    ) {
        Scaffold { paddingValues ->
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(paddingValues)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    IconButton(onClick = { onEvent(TaskEvent.DismissNewTaskSheet) }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Button(onClick = { onEvent(TaskEvent.OnTaskSubmit) }) {
                        Text(text = "Submit")
                    }
                }



                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    Text(
                        text = "Create a new task!",
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp,
                    )

                    TaskTextField(
                        value = state.title,
                        onEvent = {
                                onEvent(TaskEvent.OnTaskTitleChanged(it))
                        },
                        label = {
                            Text(text = "Title")
                        }
                    )

                    TaskTextField(
                        value = state.body,
                        onEvent = { onEvent(TaskEvent.OnTaskBodyChanged(it)) },
                        label = { Text(text = "Body") }
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        IconButton(onClick = { onEvent(TaskEvent.OpenDropdownMenu) }) {

                            if (!state.isDropDownMenuOpen) {
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowDown,
                                    contentDescription = "Open drop down menu",
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowUp,
                                    contentDescription = "Close drop down menu",
                                )
                            }


                        }



                        Spacer(modifier = Modifier.weight(1f))

                        Column(
                            horizontalAlignment = Alignment.End
                        ) {

                            AnimatedVisibility(
                                visible = state.isMoneyTextFieldEnabled
                            ) {
                                OutlinedTextField(
                                    value = state.price ?: "",
                                    onValueChange = {
                                        val regex = Regex("[\\d]*[.]?[\\d]*")
                                        if (regex.matches(it) && it.length <=6) {
                                            onEvent(TaskEvent.OnPriceChanged(it))
                                        }


                                    },
                                    modifier = Modifier
                                        .width(120.dp)
                                        .padding(end = 8.dp),
                                    singleLine = true,
                                    label = {
                                        Text(text = "$")
                                    },
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Decimal
                                    ),
                                    trailingIcon = {
                                        IconButton(
                                            onClick = {
                                                onEvent(TaskEvent.DisableMoneyTextField)
                                            },
                                            modifier = Modifier.size(20.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Rounded.Clear,
                                                contentDescription = "Clear price field",
                                                //modifier = Modifier.size(16.dp)
                                            )
                                        }
                                    }
                                )
                            }

                            val datePickerState = rememberDatePickerState()

                            DateRange(
                                state = state,
                                onEvent = onEvent,
                                datePickerState = datePickerState

                            )
                        }

                        DropdownMenu(
                            expanded = state.isDropDownMenuOpen,
                            onDismissRequest = { onEvent(TaskEvent.DismissDropdownMenu) }
                        ) {
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = "$",
                                        fontWeight = FontWeight.Bold
                                    )
                                },
                                onClick = {
                                    onEvent(TaskEvent.EnableMoneyTextField)
                                }
                            )

                            DropdownMenuItem(
                                text = {
                                    Icon(
                                        imageVector = Icons.Default.DateRange,
                                        contentDescription = "Open date picker dialog"
                                    )
                                },
                                onClick = { onEvent(TaskEvent.OpenDatePickerDialog) }
                            )

                        }


                    }



                    if (state.error) {
                        Text(
                            text = "Title and body should not be empty!",
                            fontWeight = FontWeight.Bold,
                            color = Color.hsl(0f, 1f, 0.67f)
                        )
                    }

                }
            }
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskTextField(
    value: String,
    onEvent: (String) -> Unit,
    label: @Composable () -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onEvent,
        label = label,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 6.dp, end = 6.dp)
    )
}

fun date(
    state: TaskState
): Boolean {
    return state.date != null
}