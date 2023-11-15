package com.guilherme.tasks.core

import com.guilherme.tasks.models.Task

sealed interface TaskEvent {
    data class OnTaskTitleChanged(val value: String): TaskEvent
    data class OnTaskBodyChanged(val value: String): TaskEvent
    data object OnTaskSubmit: TaskEvent
    data class SelectTask(val task: Task): TaskEvent
    data class OpenTaskDeleteDialog(val task: Task): TaskEvent
    data object DismissDeleteDialog: TaskEvent
    data object OpenNewTaskSheet: TaskEvent
    data object DismissNewTaskSheet: TaskEvent
    data object DeleteTask: TaskEvent
    data object OpenDropdownMenu: TaskEvent
    data object DismissDropdownMenu: TaskEvent

    data object EnableMoneyTextField: TaskEvent
    data object DisableMoneyTextField: TaskEvent

    data class OnPriceChanged(val price: String): TaskEvent

    //Date Range Events:
    data object OpenDatePickerDialog: TaskEvent
    data object DismissDatePickerDialog: TaskEvent
    data class PickDate(val date: Long?): TaskEvent
    data object ClearDate: TaskEvent

    //Edit Task Events:
    data object EnableEditMode: TaskEvent
    data object DismissTaskEditSheet: TaskEvent
    data class OnTitleEdit(val value: String): TaskEvent
    data class OnBodyEdit(val value: String): TaskEvent
    data class OnPriceEdit(val value: String?): TaskEvent
    data class OnDateEdit(val value: Long?): TaskEvent
    data object OnDateEditClear : TaskEvent
    data object OnTaskEditSubmit: TaskEvent


}