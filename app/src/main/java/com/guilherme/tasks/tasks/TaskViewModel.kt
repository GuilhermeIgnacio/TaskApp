package com.guilherme.tasks.tasks

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guilherme.tasks.core.TaskEvent
import com.guilherme.tasks.core.TaskState
import com.guilherme.tasks.dao.TaskDao
import com.guilherme.tasks.models.Task
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.GregorianCalendar
import java.util.TimeZone
import java.util.concurrent.TimeUnit

class TaskViewModel(
    private val dao: TaskDao
) : ViewModel() {

    private val _tasks = dao.getAll()
    private val _state = MutableStateFlow(TaskState())
    var checkboxState = mutableStateOf(true)

    val state = combine(_state, _tasks) { state, tasks ->
        state.copy(
            tasks = tasks
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TaskState())

    fun deleteTask(task: Task) {
        println(task)
    }

    fun onEvent(event: TaskEvent) {
        when (event) {
            is TaskEvent.OnTaskTitleChanged -> {

                _state.update {
                    it.copy(
                        title = event.value
                    )
                }

            }

            is TaskEvent.OnTaskBodyChanged -> {
                _state.update {
                    it.copy(
                        body = event.value
                    )
                }
            }

            TaskEvent.OnTaskSubmit -> {

                val title = state.value.title
                val body = state.value.body
                var price = state.value.price
                val date = state.value.date

                if (price != null) {
                    if (price.isBlank()) {
                        price = null
                    }
                }

                if (title.isBlank() || body.isBlank()) {
                    _state.update {
                        it.copy(
                            error = true
                        )
                    }
                } else {

                    val task = Task(
                        title = title,
                        body = body,
                        price = price?.toDoubleOrNull(),
                        date = date
                    )

                    viewModelScope.launch {
                        dao.insert(task)
                    }
                    _state.update {
                        it.copy(
                            title = "",
                            body = "",
                            price = null,
                            date = null,
                            error = false,
                            isAddNewTaskSheetOpen = false,
                            isMoneyTextFieldEnabled = false
                        )
                    }
                }


            }

            is TaskEvent.SelectTask -> {

                var price = event.task.price.toString()

                if (price == "null") {
                    price = ""
                }

                _state.update {
                    it.copy(
                        uid = event.task.uid,
                        selectedTask = event.task,
                        isTaskDetailSheetOpen = true,
                        price = price,
                    )
                }

            }

            TaskEvent.EnableEditMode -> {
                _state.update {
                    it.copy(
                        editMode = true
                    )
                }
            }

            is TaskEvent.OpenTaskDeleteDialog -> {
                _state.update {
                    it.copy(
                        isDeleteDialogOpen = true,

                        uid = event.task.uid,
                        title = event.task.title,
                        body = event.task.body,
                        date = event.task.date
                    )
                }



                println(event.task)

            }

            TaskEvent.DismissDeleteDialog -> {
                println("Delete dialog fechado")
                _state.update {
                    it.copy(
                        isDeleteDialogOpen = false,

                        uid = 0,
                        title = "",
                        body = "",
                        price = null,
                        date = null,

                        error = false

                    )
                }


            }

            TaskEvent.DeleteTask -> {

                val uid = state.value.uid
                val title = state.value.title
                val body = state.value.body
                val price = state.value.price?.toDoubleOrNull()
                val date = state.value.date

                val task = Task(
                    uid = uid,
                    title = title,
                    body = body,
                    price = price,
                    date = date
                )

                viewModelScope.launch {
                    dao.delete(task)
                    delay(300L)
                }

                _state.update {
                    it.copy(
                        isDeleteDialogOpen = false,

                        uid = 0,
                        title = "",
                        body = "",
                        price = null,
                        date = null
                    )
                }

            }

            TaskEvent.OpenNewTaskSheet -> {
                _state.update {
                    it.copy(
                        isAddNewTaskSheetOpen = true
                    )
                }
            }

            TaskEvent.DismissNewTaskSheet -> {
                _state.update {
                    it.copy(
                        isAddNewTaskSheetOpen = false,
                        uid = 0,
                        title = "",
                        body = "",
                        price = null,
                        date = null,
                        isMoneyTextFieldEnabled = false
                    )
                }
            }

            TaskEvent.OpenDropdownMenu -> {
                _state.update {
                    it.copy(
                        isDropDownMenuOpen = true,
                    )
                }

            }

            TaskEvent.DismissDropdownMenu -> {
                _state.update {
                    it.copy(
                        price = null,
                        isDropDownMenuOpen = false
                    )
                }
            }

            TaskEvent.EnableMoneyTextField -> {
                viewModelScope.launch {

                    _state.update {
                        it.copy(
                            price = null,
                            isMoneyTextFieldEnabled = true,

                            )
                    }

                    delay(100L)

                    _state.update {
                        it.copy(
                            isDropDownMenuOpen = false
                        )
                    }
                }
            }

            TaskEvent.DisableMoneyTextField -> {
                _state.update {
                    it.copy(
                        isMoneyTextFieldEnabled = false,
                        price = null
                    )
                }
            }

            is TaskEvent.OnPriceChanged -> {

                _state.update {
                    it.copy(
                        price = event.price
                    )
                }
            }

            //Date Picker Functions

            TaskEvent.OpenDatePickerDialog -> {
                _state.update {
                    it.copy(
                        isDatePickerDialogOpen = true,
                        isDropDownMenuOpen = false
                    )
                }
            }

            TaskEvent.DismissDatePickerDialog -> {
                _state.update {
                    it.copy(
                        date = null,
                        isDatePickerDialogOpen = false,
                    )
                }
            }

            is TaskEvent.PickDate -> {

                val mCalendar: Calendar = GregorianCalendar()
                val mTimeZone: TimeZone = mCalendar.timeZone
                val mGMTOffset: Int = mTimeZone.rawOffset

                val date = event.date?.minus(mGMTOffset)

                _state.update {
                    it.copy(
                        date = date,
                        isDatePickerDialogOpen = false
                    )
                }
                println(date)

            }

            TaskEvent.ClearDate -> {

                val uid = state.value.selectedTask?.uid
                val title = state.value.selectedTask?.title
                val body = state.value.selectedTask?.body
                val price = state.value.selectedTask?.price

                _state.update {
                    it.copy(
                        date = null,
                        selectedTask = uid?.let { it1 ->
                            Task(
                                uid = it1,
                                title = title.toString(),
                                body = body.toString(),
                                price = price,
                                date = null
                            )
                        }
                    )
                }

                state.value.selectedTask?.date

            }

            TaskEvent.OnTaskEditSubmit -> {

                val uid = state.value.selectedTask?.uid
                val title = state.value.selectedTask?.title
                val body = state.value.selectedTask?.body
                val price = state.value.selectedTask?.price
                val date = state.value.selectedTask?.date

                println(price)

                if (title != null && body != null) {
                    if (title.isBlank() || body.isBlank()) {
                        _state.update {
                            it.copy(
                                error = true
                            )
                        }
                    } else {

                        val task = Task(
                            uid = state.value.uid,
                            title = title.toString(),
                            body = body.toString(),
                            price = price,
                            date = date
                        )

                        viewModelScope.launch {
                            dao.update(task)
                        }

                        _state.update {
                            it.copy(
                                error = false,
                                editMode = false,
                            )
                        }


                    }

                }


            }

            is TaskEvent.OnTitleEdit -> {

                val title = state.value.selectedTask?.title
                val body = state.value.selectedTask?.body
                val price = state.value.selectedTask?.price
                val date = state.value.selectedTask?.date

                _state.update {
                    it.copy(
                        selectedTask = Task(
                            title = event.value,
                            body = body.toString(),
                            price = price,
                            date = date
                        )
                    )
                }

            }

            is TaskEvent.OnBodyEdit -> {

                val title = state.value.selectedTask?.title
                val body = state.value.selectedTask?.body
                val price = state.value.selectedTask?.price
                val date = state.value.selectedTask?.date

                _state.update {
                    it.copy(
                        selectedTask = Task(
                            title = title.toString(),
                            body = event.value,
                            price = price,
                            date = date
                        )
                    )
                }

            }

            is TaskEvent.OnPriceEdit -> {

                val title = state.value.selectedTask?.title
                val body = state.value.selectedTask?.body
                val date = state.value.selectedTask?.date

                _state.update {
                    it.copy(
                        price = event.value
                    )
                }

                _state.update {
                    it.copy(

                        selectedTask = Task(
                            title = title.toString(),
                            body = body.toString(),
                            date = date,
                            price = state.value.price?.toDoubleOrNull()
                        )
                    )
                }

            }

            is TaskEvent.OnDateEdit -> {

                val title = state.value.selectedTask?.title
                val body = state.value.selectedTask?.body
                val price = state.value.selectedTask?.price

                val mCalendar: Calendar = GregorianCalendar()
                val mTimeZone: TimeZone = mCalendar.timeZone
                val mGMTOffset: Int = mTimeZone.rawOffset

                val date = event.value?.minus(mGMTOffset)

                _state.update {
                    it.copy(
                        selectedTask = Task(
                            title = title.toString(),
                            body = body.toString(),
                            price = price,
                            date = date
                        ),
                        isDatePickerDialogOpen = false
                    )
                }

            }

            TaskEvent.OnDateEditClear -> {

            }

            TaskEvent.DismissTaskEditSheet -> {

                _state.update {
                    it.copy(
                        isTaskDetailSheetOpen = false,
                        editMode = false,
                        price = null
                    )
                }

            }

        }

    }

}
