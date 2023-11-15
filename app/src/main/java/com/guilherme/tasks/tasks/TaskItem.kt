package com.guilherme.tasks.tasks

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.guilherme.tasks.R
import com.guilherme.tasks.core.TaskEvent
import com.guilherme.tasks.core.TaskState
import com.guilherme.tasks.models.Task
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskItem(
    task: Task,
    state: TaskState,
    modifier: Modifier = Modifier,
    onEvent: (TaskEvent) -> Unit,
) {


    Column(modifier = modifier) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            if (task.price != null) {



                val formattedPrice = NumberFormat.getCurrencyInstance().format(task.price)
                Text(
                    text = formattedPrice,
                    color = Color.Green,
                )

            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Row {
                    Text(
                        text = task.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }





                Text(
                    text = task.body,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                if (task.date != null) {


                    val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val dateString = simpleDateFormat.format(task.date)

                    Text(text = dateString)
                }

            }

        }



    }

}

@Composable
fun TaskDeleteDialog(
    state: TaskState,
    onEvent: (TaskEvent) -> Unit
) {

    if (state.isDeleteDialogOpen) {
        AlertDialog(
            onDismissRequest = { onEvent(TaskEvent.DismissDeleteDialog) },
            confirmButton = {
                TextButton(onClick = { onEvent(TaskEvent.DeleteTask) }) {
                    Text(
                        text = "Delete",
                        fontWeight = FontWeight.Bold,
                        color = Color.Red
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { onEvent(TaskEvent.DismissDeleteDialog) }) {
                    Text(
                        text = "Cancel",
                    )
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Alert dialog delete task icon",
                    tint = Color.Red
                )
            },
            title = {
                Text(
                    text = "Delete",
                    color = Color.Red,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column {
                    Text(
                        text = "Are you sure you want to delete this task? This action is irreversible.",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )


                }

            }
        )
    }

}

