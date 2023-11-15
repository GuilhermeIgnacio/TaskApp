package com.guilherme.tasks.tasks

import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.guilherme.tasks.core.TaskEvent
import com.guilherme.tasks.core.TaskState
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(
    state: TaskState,
    onEvent: (TaskEvent) -> Unit,
) {

    AddTaskScreen(
        state = state,
        visible = state.isAddNewTaskSheetOpen,
        onEvent = onEvent,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    )

    TaskDetailSheet(
        visible = state.isTaskDetailSheetOpen,
        state = state,
        onEvent = onEvent,
    )

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { onEvent(TaskEvent.OpenNewTaskSheet) }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add new task"
                )
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center
        ) {

            LazyColumn {

                item {
                    Text(
                        text = "Total Tasks: ${state.tasks.size}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }


                items(state.tasks) { task ->

                    val delete = SwipeAction(
                        icon = rememberVectorPainter(Icons.Default.Delete),
                        background = Color.Red,
                        onSwipe = {
                            onEvent(TaskEvent.OpenTaskDeleteDialog(task))
                        }
                    )



                    SwipeableActionsBox(
                        startActions = listOf(delete),
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        ElevatedCard(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            ),
                        ) {
                            TaskItem(
                                task = task,
                                state = state,
                                onEvent = onEvent,
                                modifier = Modifier
                                    .clickable {
                                        onEvent(TaskEvent.SelectTask(task))
                                    }
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            )



                        }
                    }

                    TaskDeleteDialog(
                        state = state,
                        onEvent = onEvent
                    )
                }
            }
        }



    }




}