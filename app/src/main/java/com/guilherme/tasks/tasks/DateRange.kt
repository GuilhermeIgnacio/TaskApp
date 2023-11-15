package com.guilherme.tasks.tasks

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.guilherme.tasks.core.TaskEvent
import com.guilherme.tasks.core.TaskState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRange(
    state: TaskState,
    onEvent: (TaskEvent) -> Unit,
    datePickerState: DatePickerState
) {

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {


        AnimatedVisibility(
            visible = date(state)
        ) {
            val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            var dateString = state.date?.let { Date(it) }?.let { simpleDateFormat.format(it) }

            if (dateString == null) {
                dateString = ""
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = "Picked Date: $dateString",
                    fontWeight = FontWeight.Bold,
                    color = Color.Cyan
                )

                IconButton(onClick = { onEvent(TaskEvent.ClearDate) }) {
                    Icon(
                        imageVector = Icons.Rounded.Clear,
                        contentDescription = "Clear date input",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

        }


    }

    if (state.isDatePickerDialogOpen) {
        DatePickerDialog(

            onDismissRequest = {
                onEvent(TaskEvent.DismissDatePickerDialog)
            },

            dismissButton = {
                TextButton(onClick = { onEvent(TaskEvent.DismissDatePickerDialog) }) {
                    Text(text = "Cancel")
                }
            },

            confirmButton = {
                TextButton(onClick = { onEvent(TaskEvent.PickDate(datePickerState.selectedDateMillis)) }) {
                    Text(text = "Pick")
                }
            }

        ) {
            DatePicker(
                state = datePickerState,
            )
        }
    }

}

