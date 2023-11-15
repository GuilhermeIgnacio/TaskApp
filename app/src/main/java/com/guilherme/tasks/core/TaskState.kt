package com.guilherme.tasks.core

import androidx.compose.material3.DatePickerState
import com.guilherme.tasks.models.Task
import java.util.Date

data class TaskState(
    val tasks: List<Task> = emptyList(),
    val uid: Int = 0,
    val title: String = "",
    val body: String = "",
    val price: String? = null,
    val isDeleteDialogOpen: Boolean = false,
    val isAddNewTaskSheetOpen: Boolean = false,
    val isDropDownMenuOpen: Boolean = false,
    val isMoneyTextFieldEnabled: Boolean = false,
    val error: Boolean = false,

    //Date States
    val isDatePickerDialogOpen: Boolean = false,
    val date: Long? = null,

    //Task Detail
    val selectedTask: Task? = null,
    val isTaskDetailSheetOpen: Boolean = false,
    val editMode: Boolean = false

)
