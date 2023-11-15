package com.guilherme.tasks.tasks

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.guilherme.tasks.core.TaskEvent
import com.guilherme.tasks.core.TaskState
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailSheet(
    visible: Boolean,
    modifier: Modifier = Modifier,
    state: TaskState,
    onEvent: (TaskEvent) -> Unit,
) {

    Surface {

        AnimatedVisibility(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize(),
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


            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    IconButton(onClick = { onEvent(TaskEvent.DismissTaskEditSheet) }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    if (state.editMode) {

                        Button(
                            onClick = { onEvent(TaskEvent.OnTaskEditSubmit) },
                        ) {
                            Text(
                                text = "Save Changes",
                                fontWeight = FontWeight.Bold
                            )
                        }

                    } else {
                        IconButton(onClick = { onEvent(TaskEvent.EnableEditMode) }) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit Fields"
                            )
                        }
                    }


                }

                if (!state.editMode) {

                    TaskItem(state = state)


                } else {

                    Column(
                        modifier = Modifier.verticalScroll(rememberScrollState())
                    ) {
                        OutlinedTextField(
                            value = state.selectedTask?.title ?: "",
                            onValueChange = {
                                onEvent(TaskEvent.OnTitleEdit(it))
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = state.selectedTask?.body ?: "",
                            onValueChange = {
                                onEvent(TaskEvent.OnBodyEdit(it))
                            },
                            modifier = Modifier.fillMaxWidth()
                        )


                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            TextButton(
                                onClick = {},
                                modifier = Modifier.size(50.dp),
                                shape = CircleShape,
                                enabled = false
                            ) {
                                Text(
                                    text = "$",
                                    color = Color.White
                                )
                            }

                            Spacer(modifier = Modifier.weight(1f))

                            OutlinedTextField(
                                value = state.price ?: "" ,
                                onValueChange = {
                                    val regex = Regex("[\\d]*[.]?[\\d]*")
                                    if (regex.matches(it)) {
                                        onEvent(TaskEvent.OnPriceEdit(it))
                                    }
                                },
                                modifier = Modifier.width(120.dp),
                                label = {
                                    Text(text = "$")
                                }
                            )
                        }

                        val datePickerState = rememberDatePickerState(
                            initialSelectedDateMillis = state.selectedTask?.date
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = { onEvent(TaskEvent.OpenDatePickerDialog) }) {
                                Icon(
                                    imageVector = Icons.Rounded.DateRange,
                                    contentDescription = "Open date picker dialog"
                                )
                            }

                            Spacer(modifier = Modifier.weight(1f))

                            AnimatedVisibility(visible = selectedTaskDate(state)) {

                                if (state.selectedTask?.date != null) {

                                    val simpleDateFormat =
                                        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                                    val dateString = state.selectedTask.date.let {
                                        Date(it)
                                    }.let { simpleDateFormat.format(it) }

                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "Picked Date: $dateString",
                                            fontWeight = FontWeight.Bold,
                                            color = Color.Cyan
                                        )

                                        IconButton(onClick = { onEvent(TaskEvent.ClearDate) }) {
                                            Icon(
                                                imageVector = Icons.Default.Clear,
                                                contentDescription = "Clear Date")
                                        }
                                    }

                                }


                            }
                        }

                        if (state.isDatePickerDialogOpen) {

                            DatePickerDialog(
                                onDismissRequest = {
                                    onEvent(TaskEvent.DismissDatePickerDialog)
                                },
                                confirmButton = {

                                    TextButton(onClick = {
                                        onEvent(TaskEvent.OnDateEdit(datePickerState.selectedDateMillis))
                                    }) {
                                        Text(text = "Pick")
                                    }

                                },
                                dismissButton = {
                                    TextButton(onClick = {
                                        onEvent(TaskEvent.DismissDatePickerDialog)
                                    }) {
                                        Text(text = "Cancel")
                                    }
                                }
                            ) {
                                DatePicker(state = datePickerState)
                            }
                        }
                    }

                    if (state.error) {
                        Text(
                            text = "Title and body should not be empty!",
                            fontWeight = FontWeight.Bold,
                            color = Color.hsl(0f, 1f, 0.67f),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .fillMaxWidth()
                        )
                    }

                }

            }

        }
    }
}

fun selectedTaskDate(state: TaskState): Boolean {
    return state.selectedTask?.date != null
}

@Composable
fun TaskItem(
    state: TaskState,
) {
    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        border = BorderStroke(1.dp, Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .verticalScroll(rememberScrollState())

    ) {

        val padding = 12.dp

        Row(
            modifier = Modifier.padding(padding)
        ) {

            if (state.selectedTask?.date != null) {
                val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val dateString = simpleDateFormat.format(state.selectedTask.date)

                Text(
                    text = "Date: ${dateString ?: ""}",
                    color = Color.LightGray
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            if (state.selectedTask?.price != null) {
                val formattedPrice = NumberFormat
                    .getCurrencyInstance()
                    .format(state.selectedTask.price)

                Text(
                    text = formattedPrice ?: "",
                    color = Color.Green
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        )

        Row(
            modifier = Modifier.padding(padding),
            verticalAlignment = Alignment.CenterVertically
        ) {



            Text(
                text = state.selectedTask?.title ?: "-",
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Text(
            text = state.selectedTask?.body ?: "-",
            modifier = Modifier
                .padding(padding),
            textAlign = TextAlign.Center,
        )


    }
}