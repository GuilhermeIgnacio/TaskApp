package com.guilherme.tasks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.guilherme.tasks.tasks.TaskScreen
import com.guilherme.tasks.tasks.TaskViewModel
import com.guilherme.tasks.db.TaskDatabase
import com.guilherme.tasks.tasks.AddTaskScreen
import com.guilherme.tasks.ui.theme.AppTheme

class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            TaskDatabase::class.java, "tasks.db"
        ).build()
    }

    private val viewModel: TaskViewModel by viewModels<TaskViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return TaskViewModel(db.taskDao()) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            val state by viewModel.state.collectAsState()
            AppTheme {
                
                Column {
                    TaskScreen(state = state, onEvent = viewModel::onEvent )
                }

            }
        }
    }
}


